#!/bin/bash

# Enable verbose output
set -x

# Ensure log directories exist with correct permissions
mkdir -p /var/log/tomcat9
mkdir -p /usr/share/tomcat9/logs
chmod 755 /var/log/tomcat9
chmod 755 /usr/share/tomcat9/logs
chown -R tomcat:tomcat /var/log/tomcat9
chown -R tomcat:tomcat /usr/share/tomcat9/logs

# Create log files with correct permissions
touch /var/log/tomcat9/catalina.out
touch /usr/share/tomcat9/logs/catalina.out
chmod 664 /var/log/tomcat9/catalina.out
chmod 664 /usr/share/tomcat9/logs/catalina.out
chown tomcat:tomcat /var/log/tomcat9/catalina.out
chown tomcat:tomcat /usr/share/tomcat9/logs/catalina.out

# Initialize PostgreSQL if not already initialized
if [ ! -f /var/lib/postgresql/14/main/PG_VERSION ]; then
    echo "Initializing PostgreSQL data directory"
    mkdir -p /var/lib/postgresql/14/main
    chown -R postgres:postgres /var/lib/postgresql
    chmod 700 /var/lib/postgresql/14/main
    su postgres -c "/usr/lib/postgresql/14/bin/initdb -D /var/lib/postgresql/14/main"
fi

# Start PostgreSQL directly
echo "Starting PostgreSQL"
su postgres -c "/usr/lib/postgresql/14/bin/postgres -D /var/lib/postgresql/14/main" 

# Attempt multiple methods to start Tomcat
echo "Starting Tomcat"
systemctl start tomcat9 || \
service tomcat9 start || \
/usr/share/tomcat9/bin/catalina.sh start

# Create Tomcat configuration directory
TOMCAT_CONFIG_DIR="/usr/share/tomcat9/conf/"
mkdir -p "$TOMCAT_CONFIG_DIR"

# Deploy the application
WAR_FILE="/opt/hmdm-install/ROOT.war"
if [ ! -f "$WAR_FILE" ]; then
    echo "WAR file not found: $WAR_FILE"
    exit 1
fi

# Copy WAR file to Tomcat webapps
cp "$WAR_FILE" /var/lib/tomcat9/webapps/ROOT.war

# Create ROOT.xml configuration file for Tomcat
echo  '<?xml version="1.0" encoding="UTF-8"?>
<Context docBase="/var/lib/tomcat9/webapps/ROOT.war">
    <Resource name="jdbc/hmdm" auth="Container"
              type="javax.sql.DataSource"
              driverClassName="org.postgresql.Driver"
              url="jdbc:postgresql://localhost:5432/hmdm"
              username="hmdm"
              password="topsecret"
              maxTotal="20"
              maxIdle="10"
              validationQuery="SELECT 1"/>
</Context>'\
> "$TOMCAT_CONFIG_DIR/ROOT.xml"
# <?xml version="1.0" encoding="UTF-8"?>
# <Context docBase="/var/lib/tomcat9/webapps/ROOT.war">
#     <Resource name="jdbc/hmdm" auth="Container"
#               type="javax.sql.DataSource" 
#               driverClassName="org.postgresql.Driver"
#               url="jdbc:postgresql://localhost:5432/hmdm"
#               username="hmdm" 
#               password="topsecret"
#               maxTotal="20" 
#               maxIdle="10"
#               validationQuery="SELECT 1"/>
# </Context>
# EOF

# Start Tomcat directly
echo "Starting Tomcat"
/usr/share/tomcat9/bin/catalina.sh run &
wait -n 

echo "Tomcat Logs:"
ls -l /var/log/tomcat9/
ls -l /usr/share/tomcat9/logs/

# Keep the container running and show log
tail -f /var/log/tomcat9/catalina.out