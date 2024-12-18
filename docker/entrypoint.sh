#!/bin/bash

# Start Tomcat
echo "Starting Tomcat..."
/usr/local/tomcat/bin/catalina.sh start

# Start systemd to keep the container running and manage processes
echo "Starting systemd..."
exec /lib/systemd/systemd --system --unit=multi-user.target
