package stock.global.ws.handler;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class IndexDayCandleHandler extends TextWebSocketHandler {
    private final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        log.info("connect ws: {}",session.getId());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(
        @NonNull WebSocketSession session, 
        @NonNull CloseStatus status) {
        sessions.remove(session);
    }

    public void sendMessageToClients(String message) throws IOException{
        
        for (WebSocketSession session : sessions) {
            log.info("{} , {}",message , session.getId());
            session.sendMessage(new TextMessage(message));
        }
    }
}
