#!/bin/bash

###########################
## Shell script to run `mvn clean install` on each one of them and run sonarqube report
## usage for running sonarqueb: use docker sonar. --> docker run -d --name sonarqube -p 9000:9000 sonarqube
## https://hub.docker.com/_/sonarqube/
## mvn sonar:sonar -Dsonar.host.url=http://$(boot2docker ip):9000

## Assumptions that u already have installed these:
## 1.)  maven
## 2.)  docker
## 3.)  kafka
## 4.)  ubuntu/linux based os
###########################

declare -a fileDirectories
dot="$(cd "$(dirname "$0")"; pwd)"
for file in $(ls -d */)
do
  #echo "${file##}"
  fileDirectories+=($dot/${file##})
done;
#echo "${fileDirectories[@]}"
sudo systemctl start kafka
docker run -d --name sonarqube -p 9000:9000 sonarqube
for directory in ${fileDirectories[@]}
do 
    printf $directory \n
	cd $directory
	mvn clean install package test
	mvn sonar:sonar -Dsonar.host=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
done;
xdg-open http://localhost:9000
##uncomment the below line to delete running sonar docker container
#docker rm -f $(docker ps -a)
docker system prune
docker system prune -a 
