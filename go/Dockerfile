# Stage 1: Build Stage
FROM golang:1.20-alpine AS builder

ENV PROJECT_DIR=/app \
    GO111MODULE=on \
    CGO_ENABLED=0 \
    GOPRIVATE=github.com/jjh930301

WORKDIR /app

# Git과 필요한 의존성 설치
RUN apk add --no-cache git

# Go 모듈 캐싱을 위한 복사
COPY go.mod go.sum ./
RUN go mod download

# 소스 코드 복사 및 빌드
COPY . .
RUN go build -o /build/app ./cmd

# Stage 2: Runtime Stage
FROM alpine:3.19

# 필요한 패키지 설치
RUN apk add --no-cache \
    tor \
    supervisor \
    && rm -rf /var/cache/apk/*

# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 바이너리 복사
COPY --from=builder /build/app /gocron/app

# Tor 설정 파일 생성
RUN echo "SOCKSPort 0.0.0.0:9050" > /etc/tor/torrc && \
    echo "ControlPort 0.0.0.0:9051" >> /etc/tor/torrc && \
    echo "CookieAuthentication 0" >> /etc/tor/torrc && \
    echo "KeepalivePeriod 10" >> /etc/tor/torrc && \
    echo "NewCircuitPeriod 60" >> /etc/tor/torrc && \
    echo "MaxCircuitDirtiness 300" >> /etc/tor/torrc

# Supervisord 설정 파일 생성
RUN mkdir -p /etc/supervisor/conf.d
COPY <<EOF /etc/supervisor/conf.d/supervisord.conf
[supervisord]
nodaemon=true
logfile=/var/log/supervisord.log
pidfile=/var/run/supervisord.pid

[program:tor]
command=/usr/bin/tor
autorestart=true
startretries=10
startsecs=10
stopsignal=TERM
stopwaitsecs=10
redirect_stderr=true
stdout_logfile=/var/log/tor.log

[program:app]
command=/gocron/app
autorestart=true
startretries=10
startsecs=10
stopsignal=TERM
stopwaitsecs=10
redirect_stderr=true
stdout_logfile=/var/log/app.log
EOF

# 포트 노출
EXPOSE 9050 9051

# Supervisord 실행
CMD ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]