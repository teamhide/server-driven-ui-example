## Server Driven UI example
This project is an example of server driven UI.

## Stack
- Kotlin 1.9.25
- Spring Boot 3.4.0
- Webflux
- R2dbc

## Run
```shell
> docker-compose -f docker/docker-compose.yml up
```
Before start application, must have to boot up docker compose.

## Test
```shell
> docker-compose -f docker/docker-compose.yml up
> ./gradlew testAll
```
