#!/bin/bash
set -e

init_config() {
    if [ ! -f "/usr/local/tomcat/conf/Catalina/localhost/ROOT.xml" ] || [ "$FORCE_RECONFIGURE" = "true" ]; then
        echo "Initializing Tomcat configuration..."
        
        # Download HMDM WAR
        if [ ! -f "/usr/local/tomcat/webapps/ROOT.war" ] || [ "$FORCE_RECONFIGURE" = "true" ]; then
            if [ ! -z "$DOWNLOAD_CREDENTIALS" ]; then
                wget --http-user="${DOWNLOAD_CREDENTIALS%:*}" --http-password="${DOWNLOAD_CREDENTIALS#*:}" -O /usr/local/tomcat/webapps/ROOT.war "$HMDM_URL"
            else
                wget -O /usr/local/tomcat/webapps/ROOT.war "$HMDM_URL"
            fi
        fi

        # Wait for PostgreSQL
        until PGPASSWORD=$SQL_PASS psql -h "$SQL_HOST" -p "$SQL_PORT" -U "$SQL_USER" -d "$SQL_BASE" -c '\q' 2>/dev/null; do
            echo "Waiting for PostgreSQL..."
            sleep 3
        done

        # Configure database connection
        cat > "/usr/local/tomcat/conf/Catalina/localhost/ROOT.xml" << EOF
<?xml version="1.0" encoding="UTF-8"?>
<Context>
    <Resource name="jdbc/datasource"
        auth="Container"
        type="javax.sql.DataSource"
        username="${SQL_USER}"
        password="${SQL_PASS}"
        driverClassName="org.postgresql.Driver"
        url="jdbc:postgresql://${SQL_HOST}:${SQL_PORT}/${SQL_BASE}"
        maxTotal="25"
        maxIdle="10"
        validationQuery="select 1"
        validationInterval="34000"
        testOnBorrow="true"/>
</Context>
EOF
    fi
}

setup_ssl() {
    if [ "$HTTPS_LETSENCRYPT" = "true" ]; then
        if [ -z "$BASE_DOMAIN" ]; then
            echo "ERROR: BASE_DOMAIN must be set when HTTPS_LETSENCRYPT is true"
            exit 1
        fi
        
        # Generate self-signed cert for initial setup
        if [ ! -f "/usr/local/tomcat/ssl/hmdm.jks" ]; then
            keytool -genkey -noprompt \
                -alias tomcat \
                -keyalg RSA \
                -keystore /usr/local/tomcat/ssl/hmdm.jks \
                -storepass 123456 \
                -keypass 123456 \
                -validity 3650 \
                -dname "CN=${BASE_DOMAIN}, OU=Headwind MDM, O=Headwind MDM, L=City, S=State, C=US"
            echo "Generated new SSL keystore at /usr/local/tomcat/ssl/hmdm.jks"
        fi
    else
        # Use custom certificates if provided
        if [ -d "$HTTPS_CERT_PATH" ]; then
            cp "$HTTPS_CERT_PATH/$HTTPS_CERT" /usr/local/tomcat/ssl/
            cp "$HTTPS_CERT_PATH/$HTTPS_FULLCHAIN" /usr/local/tomcat/ssl/
            cp "$HTTPS_CERT_PATH/$HTTPS_PRIVKEY" /usr/local/tomcat/ssl/
            echo "Copied custom certificates from $HTTPS_CERT_PATH"
        fi
    fi
}

check_ssl() {
    # Verify SSL setup
    if [ -f "/usr/local/tomcat/ssl/hmdm.jks" ]; then
        echo "Verifying SSL keystore..."
        keytool -list -keystore /usr/local/tomcat/ssl/hmdm.jks -storepass 123456 > /dev/null 2>&1
        if [ $? -eq 0 ]; then
            echo "SSL keystore verification successful"
        else
            echo "SSL keystore verification failed"
            exit 1
        fi
    fi
}

main() {
    init_config
    setup_ssl
    check_ssl
    
    # Set shared secret
    if [ ! -z "$SHARED_SECRET" ]; then
        echo "Setting shared secret..."
        sed -i "s/SHARED_SECRET=.*/SHARED_SECRET=$SHARED_SECRET/" /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/config.properties
    fi
    
    # Start Tomcat
    catalina.sh run
}

main