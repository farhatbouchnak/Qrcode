Overview
This project aims to be a reference for those just starting to work with Java and SpringBoot in which there is a need to build an inner communication between microservices through events via Kafka. To speed up the setup of the development environment, a dockercompose is provided to provision the necessary infrastructure, i.e. Kafka and Zookeeper. Be aware of the proposed infrastructure is suitable for development and not for production operation.

Prerequisites
Maven 3+
Java 21
Docker 20+
Preparing Environment
From the project's folder, execute:
docker-compose up to initialize Kafka and Zookeeper
mvn package to build the applications
Booting Applications
Initializing the producer
$ cd producer
$ mvn spring-boot:run
Note: The producer will be accepting generating requests at http://localhost:8080/api/send

Initializing the consumer
$ cd consumer
$ mvn spring-boot:run
Note: The consumer has no endpoint, it just connects to the Kafka to listen to the stream events.

Testing
With both consumer and producer applications up and running, let's test their integration through Kafka:

$ curl -d "{'firstName': 'Firstname','lastName': 'Lastname', 'birthDate': '2025/03/25', 'vip': 'true'}" \
-H "Content-Type: application/json" \
-X POST http://127.0.0.1:8080/api/send
If the above request works, we should find a generated pass with qrcode in the response body.

Cleaning Up
After playing around, we need to clean up the mess. So from the project's folder, do:

Stop the containers using the following command:
docker-compose down
Delete all containers using the following command:
docker rm -f $(docker ps -a -q)
