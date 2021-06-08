#!/bin/bash

#git clone -b feature-mongodb https://github.com/wbjeon2k/CSE364.git
#제출 시에는 master branch!
#git clone https://github.com/wbjeon2k/CSE364.git
rm -rf CSE364
git clone -b feature-mongodb https://github.com/wbjeon2k/CSE364.git
cd ./CSE364

cp mongod_install.sh ../mongod_install.sh

cd ..
pwd
chmod a+x mongod_install.sh
./mongod_install.sh
cp mongod.conf ../../../etc/mongod.conf
#run mongod at background. logging on console is on.
mongod &
cd ./CSE364
pwd
mvn clean package
java -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
