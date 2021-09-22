#!/usr/bin/env sh
# This installs docker and other stuff needed for this program to work

sudo gradle clean && gradle build -x test && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar

