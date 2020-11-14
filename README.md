#  
  
<p align="center">  
    <strong>A micro-service application to manage Users for Recardo e-commerce platform</strong>  
</p>  
  
#  
  
  
This repository provides a REST API micro-service to handle users for [Recardo ](https://github.com/recorda-ch) organization.   
  
The implementation is based on following *technical stack* :  
- **Java 8** powered by **Spring Boot framework**  
- Spring Boot embedded **Tomcat** server (as web container)  
- **MongoDB** server (as the underlying datastore)  
- ...  
- ...  
- **Docker Containers** (for server runtimes)  
  
# Contents  
  
- [Prerequisites](#prerequisites)  
- [SetUp](#setup)  
  - [Repository setup](#repository-setup)   
  - [Docker setup](#docker-setup)   
- [Running a demo](#running-a-demo)  
- [Proof of Concept](#proof-of-concept)  
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
(enter password)  
```  
  
## Docker setup  
As mentioned above this repository contains the full REST API (deployable as a micro-service) allowing the management of Users. But you will need also to get some Docker containers and start them alongside.  
  
To retrieve the requested Docker containers , enter the following commands :  
  
In a local Terminal :  
- login to [Docker Hub](https://hub.docker.com/)   
```bash  
$ docker login --username=<username>  
(enter password)  
```  
- Retrieve containers :  
```bash  
$ docker pull mongo  
$ ...  
```  
  
# Running a demo  
  
To run the demo, you will have to :  
- start the Docker containers  
- start the API (webapp runtime)  
- use the provided Postman collection to request the API  
  
### 1) Running the containers  
```bash  
$ docker-compose up -d  
$ ...  
```  
### 2) Running the application  
```bash  
$ cd /path/to/somewhere/recorda-users  
$ mvn spring-boot:run  
```  
### 3) Launch HTTP requests on API with Postman  
Postman will be so far the tool for invoking the API.  
- open Postman IDE  
- import the provided collection (``./postman/postman-collection.json``)  
- launch the provided HTTP requests   
  
# Proof of Concept  
  
You can check the results of the demo in several ways :  
 - in MongoDB  
 - ...  
 - ### Checking results in MongoDB datastore  
You can users in MongoDB NoSQL datastore (*Documents*) with the [Mongo Shell](https://docs.mongodb.com/manual/mongo/), directly inside its dedicated docker container as below :  
```bash  
$ $ docker exec -it recardo-mongo /bin/bash  
$ mongo  
> use recardo;  
> db.users.find();  
...  
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

#
 <p align="center">  
    <strong>Enjoy !</strong>  
</p>  
  
#