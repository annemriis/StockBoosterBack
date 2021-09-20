FROM adoptopenjdk/openjdk16:ubi
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} application.jar
ADD build/libs/0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
EXPOSE 8080
