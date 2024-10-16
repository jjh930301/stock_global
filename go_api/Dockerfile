FROM golang:1.19-alpine

ENV PROJECT_DIR=/app \
    GO111MODULE=on \
    CGO_ENABLED=0 \
    GOPRIVATE=github.com/jjh930301 

ARG GITACCOUNT
ARG GITTOKEN

WORKDIR /app

RUN mkdir "/build"

COPY . .
# # install git 
RUN apk add --no-cache git tor
# # private repo config
RUN git config --global url."https://${GITACCOUNT}:${GITTOKEN}@github.com/jjh930301/needsss_common".insteadOf "https://github.com/jjh930301/needsss_common"
RUN go get github.com/githubnemo/CompileDaemon
RUN go install github.com/githubnemo/CompileDaemon@latest

RUN echo "SOCKSPort 0.0.0.0:9050" >> /etc/tor/torrc
RUN echo "CookieAuthentication 0" >> /etc/tor/torrc

RUN tor &

ENTRYPOINT sh -c "tor & CompileDaemon -build='go build -o /build/app ./cmd' -command='/build/app'"
# ENTRYPOINT CompileDaemon -build="go build -o /build/app ./cmd" -command="/build/app"