#  
  
<p align="center">    
	<strong>A REST API to manage Users for Recardo e-commerce platform</strong> 
</p>

<p align="center">    
	<img src="https://raw.githubusercontent.com/recorda-ch/recorda-users/gh-pages/Search-recardo.png"/> 
</p> 
  
#  
  
  
This repository provides a REST API micro-service to handle users for [Recardo ](https://github.com/recorda-ch) organization.   
  
The implementation is based on following *technical stack* :  
- **Java 8** powered by **Spring Boot framework**  
- Spring Boot embedded **Tomcat** server (as web container)  
- a **MongoDB** server (as the underlying datastore)  
- a **Kafka** server
- a **Zookeeper** server

 
# Contents  
  
- [Prerequisites](#prerequisites)  
- [SetUp](#setup)  
  - [Repository setup](#repository-setup)   
  - [Application setup](#application-setup)   
- [Playing the demo](#playing-the-demo)  
- [Proof of Concept](#proof-of-concept)
- [Stopping the demo](#stopping-the-demo)  
- [What else](#what-else...)  
  
# Prerequisites  
Here are the pre-requisites for running this REST API :  
- **Java 8 JDK** : *installed*  
- **Docker** and **Docker Compose** distribution : *installed*  
- **Maven** build automation tool : *installed*  
- **Postman** API client/dev tool : *installed*  
- a personnal account on [Docker Hub](https://hub.docker.com/)   
- a personnal account on [Github](https://github.com/)  
  
*(The installation of these distributions is very well documented on many sites)*  

Please note that you must have at least a basic knowledge regarding :  
- the use of **Postman** tool in order to launch HTTP requests  
- the use of **Docker** commands
  
### OS compatibility 
 
This API has been tested only under a **Linux** platform.
So please keep in mind that so far :
- its operation **under Mac OS is not guaranteed**
- And **even less under Windows OS** 
 
 
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

As mentioned above this repository contains the full REST API (deployable as a micro-service) allowing the management of Users.
This API is tightly coupled with MongoDB, Kafka and Zookeeper servers 
which are all provided as **Docker Containers**.

*Thus, you will be asked an admin password while launching scripts, why ?*
- Because docker commands require admin privileges, unless you are able to configure *privilege escalation*
*(but we will not dwell on [this subject](https://docs.docker.com/engine/install/linux-postinstall/) here)*  
 
Anyway: docker containers retrieval and application build are provided in a single script : `./initApp.sh`

First of all: please open this script and provide your [Docker Hub](https://hub.docker.com/) username in variable `DOCKERHUB_USERNAME`.
*(this is the unique intrusive action in the code - you will have to do, promised !)*

Then enter the following command in a *local Terminal* :  

```bash  
$ ./initApp.sh  
(enter your Linux sudo password and enter your DockerHub password)    
```  
  
*As mentioned this step may take a while... but finally you are able to play the demo*
  
# Playing the demo  
  
Running the demo means at least :  
- start the Docker containers  
- start the API (webapp runtime)  
- use the provided Postman collection to request the API
- *observe what happens...*  
  
### 1) Start the whole application   

Provided script : `./startApp.sh` starts fully the application (API and containers) :
 
```bash  
$ ./startApp.sh
(enter Linux sudo password)
```  
  
### 2) Use Postman to interact with Users management API  

Postman is a wonderfull tool for invoking an API.
  
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
$ ./showUsersInMongo.sh
(enter Linux sudo password)
```  

### 2) Checking event messages in Kafka 

You can display messages sent to **Kafka topic** directly inside its dedicated docker container as below :

```bash  
$ ./showKafkaTopic.sh
(enter Linux sudo password)
```
  
Here are some exemples of messages sent by API to Kafka topic :

```bash  
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"POST","payload":{"id":"5fb28bfac20cf8478e24062f","firstname":"James","lastname":"Gosling","email":"james.gosling@java.com","password":"changeme","address":"10 Place de Jargonnant, 1207 Genève","ip":"46.14.0.12"}} 
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"PATCH","payload":{"firstname":"Ryan"}}
{"resource":"USER","id":"5fb28bfac20cf8478e24062f","source":"REST","type":"PUT","payload":{"id":null,"firstname":"Ryan","lastname":"Gosling","email":"ryan.gosling@actorstudio.com","password":"iAmSoBeautiful","address":"Hollywood, California, US","ip":"45.79.19.196"}}
```  

# Stopping the demo  

To stop the demo, just launch script : `./stopApp.sh` :

```bash  
$ ./stopApp.sh
(enter Linux sudo password)
```  

# What else...

### Project governance 

- Kanban board is here : [Users Management at Recorda](https://github.com/recorda-ch/recorda-users/projects/1) 


### Still missing...

Unfortunately there are some lacks in this code, such as :

### 1) no conversion layer between *controller* and *service* !
- the controler (e.g: ``UserResource`` ) should not use directly objects of the model (e.g:``User``) , neither in its method signature nor in its returns
- there should be instead some **converters** doing the job of mapping, based on [MapStruct](https://mapstruct.org/) for instance

### 2) No Swagger documentation !

**Swagger** (so-called "**OpenAPI specification**" nowadays ) can fully document the API such as a contract.

Thus :
- this project should be swagger-annotated in order to have full Swagger documentation automatically generated with a plugin such as [Swagger Maven Plugin](https://github.com/kongchen/swagger-maven-plugin)

### 3) No validation process on fields !

There should be also validation at field level based on annotations as provided by an implementation of framework [Bean Validation](https://beanvalidation.org/)


### 4) Some caching could have been configured

- The relation *IP Source / Eligibility* could be put in a cache (managed by [Redis](https://redis.io/) caching?)
in order to minimize redundant calls to third-party API ([IP Location API](https://ipapi.co/#api)) which could be monetized.



Finally : just a matter of time !

*(my2cents)*
