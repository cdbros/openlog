# OpenLog SpringBoot service

Openlog is an opensource project for monitoring your software.

## How to run

### Prerequisites
- jdk11
- docker (optional)

First you need a working MySQL instance. 
The project is docker based, so just run the ***/script/db/create-local-db.sh*** and ***/script/db/update-db.sh***
scripts for creating the MySQL docker container and launch the liquibase update.

To run locally the backend, from the project folder run 
```bash 
./mvnw clean spring-boot:run
```

If you want to run tests locally
```bash 
./mvnw compile test
```

To test/run the backend in a container environment you can use the ***/script/build-docker-image.sh*** script.
Please note that in order to run the application without the docker compose approach (see next paragraph) you
need to create a docker network with your MySQL container and the SpringBoot one in it.

The project contains also a ***docker-compose.yml*** file if you want to create and run both containers.

## Contributing

If you want to contribute just open a pull request to the maintainers.

### Guidelines
- Follow the feature pattern for package creation.
- Write unit tests for all code written.
- Validate the code quality of your code with tools like Sonarlint
- Avoid useless comments, let your code speak for you.

## Maintainers
- Danilo Cadeddu (danilo_cadeddu@outlook.it)

## Major dependencies
- Liquibase
- Lombok