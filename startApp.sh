#!/bin/bash
# --------------------------------------------
# Start application in its Docker environment
# --------------------------------------------

# Run docker containers with Hack on IP (concerns KAFKA connectivity)
# see:
#   - https://kafka.js.org/docs/running-kafka-in-development
#   - https://github.com/wurstmeister/kafka-docker/wiki/Connectivity
sudo HOST_IP=$(ifconfig | grep -E "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d: | head -n1) docker-compose -f docker/docker-compose-all.yml up -d

# Run API micro-service runtime
mvn spring-boot:run

