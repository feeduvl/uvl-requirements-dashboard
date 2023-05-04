FROM openjdk:20
WORKDIR /uvl-requirements-dashboard
COPY . .
RUN _JAVA_OPTIONS="-Xmx2g" mvn package
RUN mvn site

EXPOSE 9645

CMD ["app"]