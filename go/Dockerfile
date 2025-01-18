FROM golang:1.20-alpine

ENV PROJECT_DIR=/app \
    GO111MODULE=on \
    CGO_ENABLED=0 \
    GOPRIVATE=github.com/jjh930301 

ARG GITACCOUNT
ARG GITTOKEN

WORKDIR /app

RUN mkdir "/build"

COPY . .
# # private repo config
# RUN go get github.com/githubnemo/CompileDaemon
RUN go install github.com/githubnemo/CompileDaemon@latest

# ENTRYPOINT sh -c "CompileDaemon -build='go build -o /build/app ./cmd' -command='/build/app'"
ENTRYPOINT CompileDaemon -build="go build -o /build/app ./cmd" -command="/build/app"