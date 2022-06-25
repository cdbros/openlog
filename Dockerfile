FROM maven:3.8.6-jdk-11

RUN mkdir -p /usr/local/openlog
COPY . /usr/local/openlog

WORKDIR /usr/local/openlog
RUN mvn clean install -DskipTests

COPY ./target/*.jar /usr/local/openlog/openlog.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/usr/local/openlog/openlog.jar"]
