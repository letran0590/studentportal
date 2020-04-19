#!/bin/bash
git fetch origin
git checkout master
git pull origin master
mvn clean install -Dmaven.test.skip=true
./run.sh