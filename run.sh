#!/bin/bash

rm -rf ./CSE364
git clone https://github.com/wbjeon2k/CSE364.git

cd ./CSE364

mvn clean package
java -Xms1g -Xmx4g -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
