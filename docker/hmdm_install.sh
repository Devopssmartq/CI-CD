#!/bin/bash

# Set the domain name
DOMAIN="thesmartq.com"

# Prepare Tomcat configuration
TOMCAT_CONFIG_DIR="/var/lib/tomcat9/conf/Catalina/localhost"
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
cat << EOF > "$TOMCAT_CONFIG_DIR/ROOT.xml"
<?xml version="1.0" encoding="UTF-8"?>
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
</Context>
EOF

# Restart Tomcat to apply changes
service tomcat9 restart

echo "Headwind MDM installation completed."
echo "Access the web panel at: http://$DOMAIN:8080"
echo "Default login: admin/admin (change immediately after first login)"