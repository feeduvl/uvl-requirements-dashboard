FROM openjdk:20
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ./target/data-jira-mongodb-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]