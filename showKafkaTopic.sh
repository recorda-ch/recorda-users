#!/bin/bash
# --------------------------------------------
# Display topic activity in Kafka container
# --------------------------------------------

# Kafka container-id
export KAFKA_CONTAINER_ID=$(docker ps -a -q --filter name=recardo-kafka)

# Display realtime Kafka topic activity
echo "[Realtime Kafka topic activity]"
sudo docker exec $KAFKA_CONTAINER_ID /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic recardo-users
