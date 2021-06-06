# 1. Download ubuntu 20.04.
FROM ubuntu:20.04

# 2.Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
RUN apt-get clean
RUN apt-get update
RUN apt-get update --fix-missing
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install openjdk-11-jdk maven vim git curl wget gnupg systemctl net-tools


# 3.Create /root/project folder
RUN mkdir /data
RUN mkdir /data/db
RUN mkdir /root/project

# and set it as WORKDIR.
WORKDIR /root/project

# 4. Add your run.sh file to /root/project in the docker container. Your run.sh must include command lines that git clone your_repository, cd your_repository, mvn instll, and java command to run your code. The details are given in Submission Instructions.
COPY mongod.conf /etc/mongod.conf
COPY run.sh /root/project/run.sh
COPY mongod_install.sh /root/project/mongdinstall.sh
RUN chmod a+x /root/project/run.sh /root/project/mongdinstall.sh /etc/mongod.conf

# 5. The container should execute a bash shell by default when the built image is launched.
ENTRYPOINT /bin/bash
