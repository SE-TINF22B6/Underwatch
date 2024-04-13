FROM openjdk:17.0.2-slim-buster

EXPOSE 8080

COPY ./build/libs/backend-0.0.1-SNAPSHOT-plain.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "backend-0.0.1-SNAPSHOT-plain.jar"]