package stock.global.ws.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import stock.global.ws.handler.DayCandleHandler;
import stock.global.ws.handler.IndexDayCandleHandler;


@Configuration
@EnableWebSocket
@EnableRedisHttpSession
public class WsConfig implements WebSocketConfigurer{

    private final IndexDayCandleHandler indexDayCandleHandler;
    private final DayCandleHandler dayCandleHandler;

    public WsConfig(
        HandshakeInterceptor handshakeInterceptor,
        IndexDayCandleHandler indexDayCandleHandler,
        DayCandleHandler dayCandleHandler
    ) {
        this.indexDayCandleHandler = indexDayCandleHandler;
        this.dayCandleHandler = dayCandleHandler;
    }

    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(indexDayCandleHandler, "/index")
            .setAllowedOriginPatterns("http://localhost:3000", "ws://localhost:3000");
        registry.addHandler(dayCandleHandler, "/")
            .setAllowedOriginPatterns("http://localhost:3000", "ws://localhost:3000");
    }
}
