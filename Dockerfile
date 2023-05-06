FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY src /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/src
COPY pom.xml /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard
RUN mvn -f /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/pom.xml clean package


FROM openjdk:17-jdk-oracle
COPY --from=build /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar jira-dashboard.jar
ENTRYPOINT ["java","-jar","jira-dashboard.jar"]