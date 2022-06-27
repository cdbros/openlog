FROM maven:3.8.6-jdk-11 AS maven

COPY . .
RUN mvn clean install -DskipTests
COPY ./target/*.jar openlog.jar

FROM alpine:3.16 AS java

RUN  apk update \
  && apk upgrade \
  && apk add --update openjdk11 \
  && rm -rf /var/cache/apk/*
COPY --from=maven openlog.jar .
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "openlog.jar"]
