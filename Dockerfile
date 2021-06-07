# 1. Download ubuntu 20.04.
FROM ubuntu:20.04

# 2.Install all packages that are needed to run your program, such as vim, git, java 11, maven, etc.
#현재 실행하는데 필요한것 전부 설치.
#mongodb 는 apt-get mongodb 로 설치 불가능해서 따로 mongod_install.sh 로 분리.
RUN apt-get clean
RUN apt-get update
RUN apt-get update --fix-missing
RUN DEBIAN_FRONTEND="noninteractive" apt-get -y install openjdk-11-jdk maven vim git curl wget gnupg systemctl net-tools


# 3.Create /root/project folder
# /data/db 디렉토리 없으면 mongodb-org 설치 오류가 나는 버그가 있음.
RUN mkdir /data
RUN mkdir /data/db

RUN mkdir /root/project

# and set it as WORKDIR.
WORKDIR /root/project

# 4. Add your run.sh file to /root/project in the docker container. Your run.sh must include command lines that git clone your_repository, cd your_repository, mvn instll, and java command to run your code. The details are given in Submission Instructions.
# dbPath 를 /data/db로 미리 설정한 conf 파일 로드.
#COPY mongod.conf /etc/mongod.conf
#COPY run.sh /root/project/run.sh
#COPY mongod_install.sh /root/project/mongod_install.sh
#RUN chmod a+x /root/project/run.sh /root/project/mongod_install.sh /etc/mongod.conf

COPY run.sh /root/project/run.sh
RUN chmod a+x /root/project/run.sh

# 5. The container should execute a bash shell by default when the built image is launched.
ENTRYPOINT /bin/bash
