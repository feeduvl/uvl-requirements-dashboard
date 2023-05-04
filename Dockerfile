FROM maven:3-jdk-8-alpine AS build


# Build Stage
WORKDIR /uvl-requirements-dashboard

COPY . .
RUN _JAVA_OPTIONS="-Xmx2g" mvn package




FROM openjdk:20

ARG JAR_FILE=target/*.jar
COPY ./target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar app.jar
ENV PORT 9645
EXPOSE $PORT
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]

