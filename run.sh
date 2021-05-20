#!/bin/bash

git clone https://github.com/wbjeon2k/CSE364.git
cd ./CSE364
mvn package
java -Xms1g -Xmx4g -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar