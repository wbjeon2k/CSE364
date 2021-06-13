# 1. Download ubuntu 20.04.
FROM ubuntu:20.04

# 2.Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
RUN apt-get clean
RUN apt-get update
RUN apt-get update --fix-missing
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install openjdk-11-jdk maven vim git curl wget gnupg systemctl net-tools unzip

# tomcat
RUN mkdir /usr/local/tomcat
RUN wget https://mirror.navercorp.com/apache/tomcat/tomcat-9/v9.0.46/bin/apache-tomcat-9.0.46.tar.gz -O /tmp/tomcat.tar.gz
RUN cd /tmp && tar xvfz tomcat.tar.gz
RUN cp -Rv /tmp/apache-tomcat-*/* /usr/local/tomcat/
RUN rm -rf /tmp/*tomcat*
RUN echo 'export CATALINA_OPTS="$CATALINA_OPTS -Xms1g -Xmx4g"' > /usr/local/tomcat/bin/setenv.sh
EXPOSE 8080
COPY TWwW.war /usr/local/tomcat/webapps/

# mongodb
RUN wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | apt-key add -
RUN echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-4.4.list
RUN rm -rf /data
RUN mkdir /data
RUN mkdir /data/db
RUN apt-get update
RUN apt-get install -y mongodb-org

# data
RUN wget --no-check-certificate 'https://docs.google.com/uc?export=download&id=1SYiy9N5fla1Rt6P3DsQOH4gFpsooj5uE' -O /data/data.zip
RUN unzip /data/data.zip -d /data/

# 3.Create /root/project folder and set it as WORKDIR.
RUN mkdir /root/project
WORKDIR /root/project

# 4. Add your run.sh file to /root/project in the docker container. Your run.sh must include command lines that git clone your_repository, cd your_repository, mvn instll, and java command to run your code. The details are given in Submission Instructions.
COPY run.sh /root/project/run.sh
RUN chmod +x /root/project/run.sh
RUN sed -i 's/\r$//' /root/project/run.sh

# 5. The container should execute a bash shell by default when the built image is launched.
#ENTRYPOINT /bin/bash

ENTRYPOINT mongod & /usr/local/tomcat/bin/catalina.sh run
