#FROM openjdk:20
#
#ARG MAVEN_VERSION=3.8.5
#ARG USER_HOME_DIR="/root"
##ARG SHA=a9b2d825eacf2e771ed5d6b0e01398589ac1bfa4171f36154d1b5787879605507802f699da6f7cfc80732a5282fd31b28e4cd6052338cbef0fa1358b48a5e3c8
#ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
#
#RUN microdnf install findutils git
#
##RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
##  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
##  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
##  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
##  && rm -f /tmp/apache-maven.tar.gz \
##  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#
#ENV MAVEN_HOME /usr/share/maven
#ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
#
#COPY mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
#COPY settings-docker.xml /usr/share/maven/ref/
#
#ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
#WORKDIR /uvl-requirements-dashboard
#COPY . .
#RUN _JAVA_OPTIONS="-Xmx2g" mvn package
#RUN mvn site
#
#ARG codecov_secret
#RUN curl -s https://codecov.io/bash >> ./codecov
#RUN chmod +x ./codecov
#RUN ./codecov -t $codecov_secret
#
#CMD ["mvn", "exec:java"]
## Build Stage

#
#
#
#
#FROM openjdk:20

#ARG JAR_FILE=target/*.jar
#COPY ./target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar app.jar
#WORKDIR /app
#
## copy pom.xml from context into image
#COPY pom.xml /uvl-requirements-dashboard/pom.xml
#
## run from /app directory which now contains a pom.xml, should work
#RUN mvn clean package
#ENV PORT 9645
#EXPOSE $PORT
#ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]

FROM maven:3-jdk-8-alpine AS build


COPY ./target/uvl-requirements-dashboard-0.0.1-SNAPSHOT.jar app.jar
WORKDIR /uvl-requirements-dashboard
RUN mvn clean
RUN mvn package

ENV PORT 9645
EXPOSE $PORT
ENTRYPOINT ["java","-jar","-Xmx1024M","-Dserver.port=${PORT}","app.jar"]