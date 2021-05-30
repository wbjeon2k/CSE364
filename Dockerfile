# 1. Download ubuntu 20.04.
FROM ubuntu:20.04

# 2.Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
RUN apt-get update
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install vim git openjdk-11-jdk maven curl wget gnupg systemctl mongodb-org


# 3.Create /root/project folder
RUN mkdir /root/project

# and set it as WORKDIR.
WORKDIR /root/project

# 4. Add your run.sh file to /root/project in the docker container. Your run.sh must include command lines that git clone your_repository, cd your_repository, mvn instll, and java command to run your code. The details are given in Submission Instructions.
COPY run.sh /root/project/run.sh
RUN chmod +x /root/project/run.sh

# 5. The container should execute a bash shell by default when the built image is launched.
ENTRYPOINT /bin/bash
