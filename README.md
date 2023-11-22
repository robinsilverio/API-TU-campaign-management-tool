# Administration tool for managing campaigns and price-promotions for Webshop
 
## 1. Description
The purpose of this application is to function as an intermediate between the frontend of campaign management tool
and the database for managing campaigns and pricepromotions in the webshop.

## 2. Required software
It is required to have java installed, preferably of version 17 (17.0.9). And for running mvn-commands, it is required to install
maven, preferably of version >= 3.

Link to download maven: https://maven.apache.org/download.cgi

## 3. Installation instructions

1. Clone the project by using the command
```git clone ssh://git@bitbucket.technischeunie.com:7999/cs/campaign-admin-backend.git```
2. Make sure that all maven dependencies are installed. Normally, maven re-imports the dependencies automatically
on startup of IDE
3. Make sure that you have installed the required software for running the application and commands (see section 2)
4. Run command ```mvn clean install``` for creating a brand new build plus package of the project (optional)
5. To run the application, simply go to the application main class called TuCMTApiApplication.java

## 4. Running tests:
For running tests, simply run the command ```mvn test``` or by accessing the testfiles that are stored in src/test
and from there run these manually. Also, the test suite class is called TuCMTApiApplicationTests.java

## 5. Testing API-requests
Using http-files located in http folder in project structure. Use auth.http for doing request obtaining token so
that you can test requests of adding campaigns etc.

## 6. Local deployment using docker
If you want to deploy the application locally using a docker container, you can use docker commands to perform that.
Keep in mind that it is required to install docker engine.
Link to docker installation: https://docs.docker.com/engine/install/

### Steps to reproduce
Below are the steps for ensuring the app deployment:

#### Step 1 - Ensure that a Dockerfile and compose file are stored in the project structure.
For now it is not needed, but if there are absence of these files, you must add these.
Dockerfile:
```
FROM eclipse-temurin:17.0.1_12-jre-alpine
MAINTAINER technischeunie.com
ARG JAR_FILE=target/TU_campaign_management_tool_API-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /TU_campaign_management_tool_API-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/TU_campaign_management_tool_API-0.0.1.jar"]
LABEL com.jfrog.artifactory.retention.maxCount="10"
```
Compose file (store this in a sub directory _docker/_ inside the project structure):
```yml
version: '3.0'
services:
  tu-campaign-management-tool-backend:
    image: tu-campaign-backend:0.0.1-SNAPSHOT
    ports:
      - 8080:8080
    restart: always
    environment:
      - "SPRING_PROFILES_ACTIVE=dev"
```
#### Step 2 - Open terminal and navigate to the project.
Use command ```cd <path to the project>```
#### Step 3 - Create a docker image.
Issue command to create a docker-image. Remember to use the name of the image that is
declared in the compose file.

Command for creating an docker image:
```bash
docker build -t tu-campaign-backend:0.0.1-SNAPSHOT .
```
#### Step 4 - Issue command docker compose to start deployment.
Command:
```bash
docker-compose -f <path to compose yml-file> up -d
```

After issuing the command, you can access the website by url http://localhost:5173

If you want to stop the deployment locally, then issue the command below to stop it.
```
docker-compose -f <path to compose yml-file> down
```

## 7. Troubleshoot
Sometimes it can be a hard time and time consuming to find solutions for problems that
you encounter. But lets list a couple of problems with its solutions that happens during development.

### Error during connect.
Error message after issuing command docker ps (listing containers):
```
$ docker ps
error during connect: this error may indicate that the docker daemon is not running: Get "http://%2F%2F.%2Fpipe%2Fdocker_engine/v1.24/containers/json": open //./pipe/docker_engine: Het systeem kan het opgegeven
 bestand niet vinden.
```
If you see this warning when issuing a command like _docker ps_. It means that a docker-daemon (engine) is not running.
For that, you should open Docker Desktop.