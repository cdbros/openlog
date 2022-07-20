FROM maven:3.8.6-jdk-11 AS maven

COPY . .
RUN pwd && ls -la
RUN mvn clean install -DskipTests
COPY target/*.jar openlog.jar

FROM openjdk:11.0.11-jre-slim

COPY --from=maven openlog.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "openlog.jar"]
