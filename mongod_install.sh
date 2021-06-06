#!/bin/bash
#this is a shell script
#to install original mongodb-org
#do not edit this!!
wget -qO - https://www.mongodb.org/static/pgp/server-4.4.asc | apt-key add -
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/4.4 multiverse" | tee /etc/apt/sources.list.d/mongodb-org-4.4.list
# data/db is made to prevent install bug
rm -rf ../../data
mkdir ../../data
mkdir ../../data/db
apt-get update
apt-get install -y mongodb-org