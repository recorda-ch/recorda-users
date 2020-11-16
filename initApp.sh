#!/bin/bash
# -----------------
# Init application
# -----------------

# Please provide your DockerHub username
export DOCKERHUB_USERNAME=dockersylvainr

# Init your DockerHub account
sudo docker login --username=${DOCKERHUB_USERNAME}

# Pull requested docker containers
# (Please notice that this will be take time...)
sudo docker pull mongo
sudo docker pull wurstmeister/kafka
sudo docker pull wurstmeister/zookeeper

echo "Please notice that this step may take a while..."
echo "Be patient, :-)"

# Build application
mvn clean install

