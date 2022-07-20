FROM maven:3.8.6-jdk-11 AS maven

COPY . .
RUN mkdir target
RUN mvn clean install -DskipTests
COPY ./target/openlog.jar openlog.jar

FROM openjdk:11.0.11-jre-slim

COPY --from=maven openlog.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "openlog.jar"]
