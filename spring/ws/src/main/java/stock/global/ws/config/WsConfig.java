package stock.global.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import stock.global.ws.handler.IndexDayCandleHandler;


@Configuration
@EnableWebSocket
@EnableRedisHttpSession
public class WsConfig implements WebSocketConfigurer{

    private final HandshakeInterceptor handshakeInterceptor;
    private final IndexDayCandleHandler indexDayCandleHandler;

    public WsConfig(
        HandshakeInterceptor handshakeInterceptor,
        IndexDayCandleHandler indexDayCandleHandler
    ) {
        this.indexDayCandleHandler = indexDayCandleHandler;
        this.handshakeInterceptor = handshakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(indexDayCandleHandler, "/{token}")
            .addInterceptors(handshakeInterceptor)
            .setAllowedOriginPatterns("http://localhost:3000", "ws://localhost:3000");
    }
}
