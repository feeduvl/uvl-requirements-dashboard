FROM openjdk:20

#COPY pom.xml /uvl-requirements-dashboard/pom.xml
#WORKDIR /uvl-requirements-dashboard
#COPY . .
#RUN mvn clean
#RUN mvn package

ARG JAR_FILE=target/*.jar
COPY ./target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9645
ENTRYPOINT ["java","-jar","/app.jar"]
#CMD ["mvn", "exec:java"]