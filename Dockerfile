FROM adoptopenjdk/openjdk16:ubi
ADD build/libs/ito0302-2021-back-end-0.0.1.jar ito0302-2021-back-end-0.0.1.jar
ENV LANG C.UTF-8
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "ito0302-2021-back-end-0.0.1.jar"]
EXPOSE 8080
