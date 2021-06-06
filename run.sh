#!/bin/bash

./mongdinstall.sh
rm -rf CSE364
git clone -b feature-mongodb https://github.com/wbjeon2k/CSE364.git
cd ./CSE364
mongod &
mvn clean package
java -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
