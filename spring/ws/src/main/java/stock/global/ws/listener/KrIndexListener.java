package stock.global.ws.listener;

import java.io.IOException;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import stock.global.ws.handler.IndexDayCandleHandler;

@Slf4j
@Component
public class KrIndexListener implements StreamListener<String, MapRecord<String, String, String>> {

    private final IndexDayCandleHandler handlers;

    public KrIndexListener(IndexDayCandleHandler handlers) {
        this.handlers = handlers;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        try {
            String streamData = message.getValue().get("data");
            this.handlers.sendMessageToClients(streamData);
            
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}