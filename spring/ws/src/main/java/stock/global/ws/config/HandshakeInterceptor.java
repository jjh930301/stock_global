package stock.global.ws.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import lombok.extern.slf4j.Slf4j;
import stock.global.core.util.JwtUtil;

@Slf4j
@Component
public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final JwtUtil jwtUtil;

    public HandshakeInterceptor(
        JwtUtil jwtUtil
    ) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(
        @NonNull ServerHttpRequest request, 
        @NonNull ServerHttpResponse response, 
        @NonNull WebSocketHandler wsHandler, 
        @NonNull Map<String, Object> attributes
    ) throws Exception {
        
        String token = request.getURI().getPath(); // Authorization 헤더를 통해 토큰 받기
        log.info("token:::{}",token);

        return super.beforeHandshake(request, response, wsHandler, attributes);
    }
}