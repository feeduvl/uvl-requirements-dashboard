FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY src /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/src
COPY pom.xml /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard
RUN mvn -f /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/pom.xml clean install


FROM openjdk:17-jdk-oracle
COPY --from=build /Users/benja/IdeaProjects/Masterarbeit/uvl-requirements-dashboard/target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar uvl-requirements-dashboard.jar
EXPOSE 9645
ENTRYPOINT ["java","-jar","uvl-requirements-dashboard.jar"]
CMD ["mvn", "exec:java"]