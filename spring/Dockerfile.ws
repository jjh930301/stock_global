# Stage 1: Build Stage
FROM gradle:8.12-jdk21 AS builder

WORKDIR /wsapp

COPY ws /wsapp/ws
COPY core /wsapp/core
COPY build.gradle /wsapp/
COPY settings.gradle /wsapp/

RUN gradle dependencies

RUN gradle ws:build core:build

FROM openjdk:21-slim

WORKDIR /wsapp

COPY --from=builder /wsapp/ws/build/libs/ws-*.jar /wsapp/ws.jar

ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8100

CMD ["java", "-jar", "/wsapp/ws.jar"]