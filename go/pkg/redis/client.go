package redis

import (
	"context"
	"os"

	configRedis "github.com/redis/go-redis/v9"
)

var RedisCtx = context.Background()
var Client *configRedis.ClusterClient

// Redis 클러스터 클라이언트 설정
func ConnectClient() *configRedis.ClusterClient {
	return configRedis.NewClusterClient(&configRedis.ClusterOptions{
		Addrs: []string{
			os.Getenv("REDIS_MASTER"),
			os.Getenv("REDIS_NODE1"),
			os.Getenv("REDIS_NODE2"),
		},
		Password: os.Getenv("REDIS_PASSWORD"),
	})
}
