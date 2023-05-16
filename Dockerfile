FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY src /src
COPY pom.xml /
RUN mvn -f pom.xml clean package

FROM openjdk:17-jdk-oracle
COPY --from=build /target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar uvl-requirements-dashboard.jar
EXPOSE 9645
CMD ["java","-jar","uvl-requirements-dashboard.jar"]
