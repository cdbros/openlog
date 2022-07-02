#!/bin/bash

MYSQL_HOST=127.0.0.1
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=root
MYSQL_SCHEMA=openlog

java -jar ../../liquibase/lib/liquibase-core-4.3.1.jar --changeLogFile="../../liquibase/changeLog-master.xml" --username="$MYSQL_USER" --password="$MYSQL_PASSWORD" --url="jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/$MYSQL_SCHEMA?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" --classpath="../../liquibase/lib/mysql-connector-java-8.0.19.jar" --driver="com.mysql.cj.jdbc.Driver" --logLevel="info" update
