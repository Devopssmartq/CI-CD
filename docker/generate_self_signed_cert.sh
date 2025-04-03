#!/bin/bash

# Create a self-signed SSL certificate for localhost
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/ssl/private/tomcat.key \
    -out /etc/ssl/certs/tomcat.crt \
    -subj "/C=US/ST=Local/L=Local/O=Development/CN=localhost"

# Set correct permissions
chmod 640 /etc/ssl/private/tomcat.key
chmod 644 /etc/ssl/certs/tomcat.crt