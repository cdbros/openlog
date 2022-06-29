@echo off

set MYSQL_HOST=127.0.0.1
set MYSQL_PORT=3306
set MYSQL_USER=root
set MYSQL_PASSWORD=root
set MYSQL_SCHEMA=openlog

java -jar ../../liquibase/lib/liquibase-core-4.3.1.jar --changeLogFile="../../liquibase/changeLog-master.xml" --username="%MYSQL_USER%" --password="%MYSQL_PASSWORD%" --url="jdbc:mysql://%MYSQL_HOST%:%MYSQL_PORT%/%MYSQL_SCHEMA%?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" --classpath="../../liquibase/lib/mysql-connector-java-8.0.19.jar" --driver="com.mysql.cj.jdbc.Driver" --logLevel="info" update
