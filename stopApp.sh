#!/bin/bash
# --------------------------------------------
# Stop application and its Docker environment
# --------------------------------------------

# Stop/Remove docker containers
sudo docker-compose -f docker/docker-compose-all.yml down

# Stop API micro-service runtime
export PIDOF_API=$(lsof -i -P -n | grep 8081 | awk '{ print $2 }')
kill -9 $PIDOF_API

