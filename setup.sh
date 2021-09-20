#!/usr/bin/env sh

gradle clean build -DskipTests
cp target/docker-spring-boot-postgres-0.0.1-SNAPSHOT.jar src/main/docker

FROM adoptopenjdk/openjdk16:ubi
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
