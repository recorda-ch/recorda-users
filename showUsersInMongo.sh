#!/bin/bash
# ---------------------
# Show users in MongoDB
# ---------------------

# MongoDB container-id
export MONGO_CONTAINER_ID=$(docker ps -a -q --filter name=recardo-mongo)

# Display realtime Kafka topic activity
echo "[Users in MongoDB datastore]"
sudo docker exec $MONGO_CONTAINER_ID mongo recardo --eval 'db.users.find();'
