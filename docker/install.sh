#!/bin/bash
set -e

# Generate SSL certificate
generate_ssl_cert() {
    keytool -genkey -noprompt \
        -alias tomcat \
        -keyalg RSA \
        -keystore /usr/local/tomcat/ssl/hmdm.jks \
        -keypass 123456 \
        -storepass 123456 \
        -dname "CN=thesmartq.com, OU=Development, O=Company, L=City, S=State, C=US"
    
    chmod 644 /usr/local/tomcat/ssl/hmdm.jks
}

init_postgresql() {
    service postgresql start
    
    until pg_isready; do
        echo "Waiting for PostgreSQL..."
        sleep 2
    done

    su - postgres -c "psql -tc \"SELECT 1 FROM pg_user WHERE usename = 'hmdm'\" | grep -q 1 || psql -c \"CREATE USER hmdm WITH PASSWORD 'topsecret'\""
    su - postgres -c "psql -tc \"SELECT 1 FROM pg_database WHERE datname = 'hmdm'\" | grep -q 1 || psql -c \"CREATE DATABASE hmdm WITH OWNER=hmdm\""
}

configure_tomcat() {
    mkdir -p /usr/local/tomcat/ssl
    generate_ssl_cert
    chown -R root:root /opt/tomcat
    chmod -R g+r /opt/tomcat/conf
    chmod g+x /opt/tomcat/conf
}

install_hmdm() {
    cd /opt/hmdm-install
    
    cat > install.conf << EOF
TOMCAT_HOME=/opt/tomcat
POSTGRESQL_USER=hmdm
POSTGRESQL_PASSWORD=topsecret
POSTGRESQL_DB=hmdm
BASE_URL=http://localhost:8080
EOF
    
    ./install.sh
}

main() {
    mkdir -p /usr/local/tomcat/ssl
    mkdir -p /var/lib/tomcat9/conf/Catalina/localhost
    
    init_postgresql
    configure_tomcat
    
    /opt/tomcat/bin/startup.sh
    
    until curl -s http://localhost:8080 > /dev/null || [ $? -eq 7 ]; do
        echo "Waiting for Tomcat to start..."
        sleep 2
    done
    
    install_hmdm
    
    tail -f /opt/tomcat/logs/catalina.out
}

main