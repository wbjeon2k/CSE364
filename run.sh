#!/bin/bash
git clone -b feature-jquery-practice https://github.com/wbjeon2k/CSE364.git
cd ./CSE364
mvn clean package
java -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
