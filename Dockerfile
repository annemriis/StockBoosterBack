FROM adoptopenjdk/openjdk16:ubi
ADD build/libs/ito0302-2021-back-end-0.0.1.jar ito0302-2021-back-end-0.0.1.jar
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080
