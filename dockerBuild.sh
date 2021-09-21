#!/usr/bin/env sh

# shellcheck disable=SC1001
docker build --build-arg JAR_FILE=C:\Users\Admin\IdeaProjects\ito0302-2021-back-end\build\libs\ito0302-2021-back-end-0.0.1.jar -t springio/gs-spring-boot-docker .