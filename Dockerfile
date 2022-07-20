FROM maven:3.8.6-jdk-11 AS maven

COPY . /openlog
RUN pwd && ls -la
WORKDIR /openlog
RUN mvn clean install -DskipTests
RUN pwd && ls -la && ls -la ./target
COPY ./target/*.jar openlog.jar

FROM openjdk:11.0.11-jre-slim

COPY --from=maven openlog.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "openlog.jar"]
