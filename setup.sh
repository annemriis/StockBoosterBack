#!/usr/bin/env sh
./gradlew clean build -x test && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar

