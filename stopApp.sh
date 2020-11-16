#!/bin/bash
# --------------------------------------------
# Stop application and its Docker environment
# --------------------------------------------

# Stop/Remove docker containers
docker-compose -f docker/docker-compose-all.yml down

# Stop API micro-service runtime
mvn spring-boot:stop

