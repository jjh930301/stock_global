# stock global

## project environment

- jdk21

- go1.20

- docker27.2.0

## run project(macos)

1. cd go/cmd

2. go get

3. cd ../../spring && ./gradlew build

4. cd .. && docker-compose -f compose.datastore.yml up --build -d

5. docker-compose -f compose.module.yml up --build

## directory struct

## go

```
📦go
┣ 📂cmd
┃ ┗ 📜main.go
┣ 📂pkg
┃ ┣ 📂api
┃ ┃ ┣ 📂daycandle
┃ ┃ ┣ 📂ticker
┃ ┃ ┣ 📜endpoint.go
┃ ┃ ┗ 📜router.go
┃ ┣ 📂constant
┃ ┣ 📂cron
┃ ┃ ┣ 📂scheduler
┃ ┃ ┗ 📜scheduler.go
┃ ┣ 📂db
┃ ┣ 📂docs
┃ ┣ 📂enum
┃ ┣ 📂models
┃ ┃ ┣ 📂res
┃ ┣ 📂repositories
┃ ┣ 📂structs
┃ ┗ 📂utils
┣ 📜Dockerfile
┣ 📜go.mod
┣ 📜go.sum
┗ 📜swag.sh
```

## spring

```
📦spring
┣ 📂.gradle
┣ 📂api
┃ ┣ 📂.gradle
┃ ┣ 📂bin
┃ ┣ 📂build
┃ ┣ 📂gradle
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┣ 📂java
┃ ┃ ┃ ┃ ┗ 📂stock
┃ ┃ ┃ ┃ ┃ ┗ 📂global
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂api
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dao
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂domain
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂auth
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂member
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂ticker
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repositories
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ApiApplication.java
┃ ┃ ┃ ┗ 📂resources
┃ ┃ ┃ ┃ ┣ 📂mappers
┃ ┣ 📜.gitignore
┃ ┣ 📜HELP.md
┃ ┣ 📜build.gradle
┃ ┣ 📜gradlew
┃ ┗ 📜gradlew.bat
┣ 📂build
┣ 📂core
┃ ┣ 📂.gradle
┃ ┣ 📂bin
┃ ┣ 📂build
┃ ┣ 📂src
┃ ┃ ┣ 📂main
┃ ┃ ┃ ┗ 📂java
┃ ┃ ┃ ┃ ┗ 📂stock
┃ ┃ ┃ ┃ ┃ ┗ 📂global
┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂core
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂annotations
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂constants
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entities
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂enums
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exceptions
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂models
┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
┃ ┗ 📜build.gradle
┣ 📂gradle
┣ 📂src
┃ ┗ 📂main
┃ ┃ ┗ 📂resources
┃ ┃ ┃ ┣ 📜application-dev.yaml
┃ ┃ ┃ ┣ 📜application-local.yaml
┃ ┃ ┃ ┗ 📜application.yaml
┣ 📜.gitignore
┣ 📜Dockerfile.dev
┣ 📜build.gradle
┣ 📜gradlew
┣ 📜gradlew.bat
┣ 📜run-api.sh
┗ 📜settings.gradle
```
