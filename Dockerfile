FROM adoptopenjdk/openjdk16:ubi
ADD build/libs/ito0302-2021-back-end-0.0.1.jar ito0302-2021-back-end-0.0.1.jar
ENV APP_HOME=/usr/app/
COPY build/libs/ito0302-2021-back-end-0.0.1.jar ito0302-2021-back-end-0.0.1.jar
ENTRYPOINT ["java", "-jar", "ito0302-2021-back-end-0.0.1.jar"]
EXPOSE 8080
