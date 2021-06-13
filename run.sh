#!/bin/bash

#git clone -b feature-mongodb https://github.com/wbjeon2k/CSE364.git
#제출 시에는 master branch!
rm -rf ./CSE364
git clone https://github.com/wbjeon2k/CSE364.git

cp ./CSE364/mongod_install.sh ./
sed -i 's/\r$//' ./mongod_install.sh

chmod a+x mongod_install.sh
./mongod_install.sh
#cp mongod.conf /etc/mongod.conf

mongod &
cd ./CSE364

mvn clean package
java -Xms1g -Xmx4g -jar target/cse364-project-1.0-SNAPSHOT-allinone.jar
