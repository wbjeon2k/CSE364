#!/bin/bash

git clone https://github.com/wbjeon2k/CSE364.git
cd ./CSE364
mvn install
java -cp ./target/cse364-project-1.0-SNAPSHOT-jar-with-dependencies.jar kr.twww.mrs.Main Adventure educator