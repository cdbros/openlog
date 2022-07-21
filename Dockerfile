FROM maven:3.8.6-jdk-11 AS maven

COPY . /openlog
WORKDIR /openlog
RUN mvn clean install -DskipTests

FROM openjdk:11.0.11-jre-slim

COPY --from=maven /openlog/target/*.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "openlog.jar"]
