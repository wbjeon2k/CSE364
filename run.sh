#!/bin/bash

./mongod_install.sh
rm -rf CSE364
git clone -b feature-mongodb https://github.com/wbjeon2k/CSE364.git
cd ./CSE364
#run mongod at background. logging on console is on.
mongod &
mvn clean package
java -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
