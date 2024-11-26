FROM ubuntu:latest

RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/* && \ wget https://ftp.azul.com/releases/Zing/ZVM24.10.0.0/zing24.10.0.0-4-jdk17.0.12.0.101-linux_aarch64.tar.gz?_gl=1*3hg63a*_gcl_au*NjU3NjI1NjQ0LjE3MzE5OTgwMTA.*_ga*MTE1MTg4MTc1LjE3MzE5OTgwMTE.*_ga_42DEGWGYD5*MTczMjA4MDM5MS40LjEuMTczMjA4MjIxNS41Ny4wLjA.  && \
    tar -xzf zing24.10.0.0-4-jdk17.0.12.0.101-linux_aarch64.tar.gz 

# Verify Java installation
RUN java -version

# Set the default command to keep the container running
CMD ["sleep", "99d"]