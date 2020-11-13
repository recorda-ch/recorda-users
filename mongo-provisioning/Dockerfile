FROM mongo

COPY initCollection.json /initCollection.json
CMD mongoimport --host mongodb --db recardo --collection users --type json --file /initCollection.json --jsonArray