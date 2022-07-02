#!/bin/bash

MYSQL_CONTAINER_PORT=3306
MYSQL_LOCAL_PORT=3306
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=openlog

docker run --name mysql8 -e "MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD" -e "MYSQL_DATABASE=$MYSQL_DATABASE" -dp $MYSQL_LOCAL_PORT:$MYSQL_CONTAINER_PORT mysql:8.0