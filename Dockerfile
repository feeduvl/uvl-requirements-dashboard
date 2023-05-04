FROM openjdk:20

ARG JAR_FILE=target/*.jar
COPY ./target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]