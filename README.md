#  
  
<p align="center">  
    <strong>A micro-service application to manage Users for Recardo e-commerce platform</strong>  
</p>  
  
#  
  
  
This repository provides a REST API micro-service to handle users for [Recardo ](https://github.com/recorda-ch) organization.   
  
The implementation is based on following *technical stack* :  
- **Java 8** powered by **Spring Boot framework**  
- Spring Boot embedded **Tomcat** server (as web container)  
- a **MongoDB** server (as the underlying datastore)  
- a **Kafka** server
- a **Zookeeper** server

MongoDB, Kafka and Zookeeper servers are provided as **Docker Containers**.  
  
# Contents  
  
- [Prerequisites](#prerequisites)  
- [SetUp](#setup)  
  - [Repository setup](#repository-setup)   
  - [Application setup](#application-setup)   
- [Playing the demo](#playing-the-demo)  
- [Proof of Concept](#proof-of-concept)
- [Stopping the demo](#stopping-the-demo)  
- [Still missing](#still-missing...)  
  
# Prerequisites  
Here are the pre-requisites for running the application :  
- **Java 8 JDK** : *installed*  
- **Docker** and **Docker Compose** distribution : *installed*  
- **Maven** build automation tool : *installed*  
- **Postman** API client/dev tool : *installed*  
- a personnal account on [Docker Hub](https://hub.docker.com/)   
- a personnal account on [Github](https://github.com/)  
  
*(The installation of these distributions is very well documented on many sites.)*  
  
Please note that you must have at least a basic knowledge regarding :  
- the use of **Docker** commands  
- the use of **Postman** tool to launch HTTP requests  
  
  
# Setup  
  
## Repository setup  
First of all you have to get the entire GIT repository on Github  
  
In a local Terminal :  

```bash  
$ cd /path/to/somewhere  
$ git clone https://github.com/recorda-ch/recorda-users.git  
(enter GitHub username and/or password)  
```  
  
## Application setup  

As mentioned above this repository contains the full REST API (deployable as a micro-service) allowing the management of Users. But you will need also to get some Docker containers and start them alongside.  

 
Docker containers retrieval and application build are provided in a single script : `./initApp.sh`

First of all, please open this script and provide your [Docker Hub](https://hub.docker.com/) username in variable `DOCKERHUB_USERNAME`.

Then enter the following command in a *local Terminal* :  

```bash  
$ ./initApp.sh  
(enter Linux sudo password, then enter DockerHub password)    
```  
  
*As mentioned this step may take a pretty much time...*
  
# Playing the demo  
  
To run the demo, you will have to :  
- start the Docker containers  
- start the API (webapp runtime)  
- use the provided Postman collection to request the API  
  
### 1) Running the docker containers and the API  

```bash  
$ ./startApp.sh
(enter Linux sudo password)
```  
  
### 2) Launch HTTP requests on API with Postman  

Postman will be so far the tool for invoking the API.  
- open Postman IDE  
- import the provided collection (``./postman/postman-collection.json``)  
- launch the provided HTTP requests   

Please notice that you may have to copy `user id` as returned in some responses to 
invoke further requests that require it.

*(sorry for that : Postman collection should be more automatized, more scripted in the future...)*
  
# Proof of Concept  
  
You can check the results of the demo in several ways :  
 - in MongoDB  
 - in Kafka
 - with Postman responses  

### 1) Checking Users status in MongoDB datastore  

You can get users in MongoDB NoSQL datastore (*Documents*) with the [Mongo Shell](https://docs.mongodb.com/manual/mongo/), directly inside its dedicated docker container as below :  

```bash  
$ sudo docker exec -it recardo-mongo /bin/bash  
$ mongo  
> use recardo;  
> db.users.find();  
```  

### 2) Checking event messages in Kafka 

You can display messages sent to **Kafka topic** directly inside its dedicated docker container as below :

```bash  
$ sudo docker exec -it recardo-kafka /bin/bash  
$ kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic recardo-users  
```
  
Here are some exemples of messages sent by API to Kafka topic :

```bash  
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"POST","payload":{"id":"5fb28bfac20cf8478e24062f","firstname":"James","lastname":"Gosling","email":"james.gosling@java.com","password":"changeme","address":"10 Place de Jargonnant, 1207 Genève","ip":"46.14.0.12"}} 
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"PATCH","payload":{"firstname":"Ryan"}}
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"PUT","payload":{"id":null,"firstname":"Ryan","lastname":"Gosling","email":"ryan.gosling@actorstudio.com","password":"iAmSoBeautiful","address":"Hollywood, California, US","ip":"45.79.19.196"}}
```  




# Stopping the demo  

```bash  
$ ./stopApp.sh
(enter Linux sudo password)
```  


# Still missing...

So far there are some lacks in this code, such as :

### 1) no conversion layer between *controller* and *service* !
- the controler (e.g: ``UserResource`` ) should not use directly objects of the model (e.g:``User``) , neither in its method signature nor in its returns
- there should be instead some **converters** doing the job of mapping, based on [MapStruct](https://mapstruct.org/) for instance

### 2) No Swagger documentation !

**Swagger** (so-called "**OpenAPI specification**" nowadays ) can fully document the API such as a contract.

Thus :
- this project should be swagger-annotated in order to have full Swagger documentation automatically generated with a plugin such as [Swagger Maven Plugin](https://github.com/kongchen/swagger-maven-plugin)

### 3) No validation process on fields !

There should be also validation at field level based on annotations as provided by an implementation of framework [Bean Validation](https://beanvalidation.org/)


*my2cents !*

