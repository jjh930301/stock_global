package stock.global.ws.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStreamCommands;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamInfo.XInfoGroups;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

import io.lettuce.core.RedisCommandExecutionException;
import lombok.extern.slf4j.Slf4j;
import stock.global.core.constants.Constant;
import stock.global.ws.listener.KrIndexListener;

@Slf4j
@Configuration
public class RedisStreamConfig {

    @Bean
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> streamMessageListenerContainer(
        RedisConnectionFactory redisConnectionFactory,
        RedisTemplate<String, String> redisTemplate,
        KrIndexListener krIndexListener
    ) {
        redisTemplate.execute((RedisConnection connection) -> {
            RedisStreamCommands streamCommands = connection.streamCommands();
        
            this.createKrIndexStream(streamCommands, redisTemplate);
            return null;
        });

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer =
            StreamMessageListenerContainer.create(redisConnectionFactory);

        listenerContainer.receiveAutoAck(
            Consumer.from(Constant.KR_CANDLE_GROUP, Constant.KR_CANDLE_GROUP_NAME),
            StreamOffset.create(Constant.KR_INDEX_DAY_CANDLE_STREAM, ReadOffset.lastConsumed()),
            krIndexListener
        );

        listenerContainer.start();
        return listenerContainer;
    }

    private void createKrIndexStream(RedisStreamCommands streamCommands, RedisTemplate<String, String> redisTemplate) {
        String streamKey = Constant.KR_INDEX_DAY_CANDLE_STREAM;
        byte[] streamKeyBytes = streamKey.getBytes();
    
        // ✅ 먼저 스트림이 존재하는지 확인
        if (!redisTemplate.hasKey(streamKey)) {
            log.info("Stream '{}' does not exist. Creating a new stream...", streamKey);
    
            // 스트림이 없으면 빈 메시지 추가하여 생성
            redisTemplate.opsForStream().add(streamKey, Collections.singletonMap("init", "1"));
        }
    
        try {
            XInfoGroups groups = streamCommands.xInfoGroups(streamKeyBytes);
    
            if (groups != null && groups.stream().anyMatch(group -> group.toString().contains(Constant.KR_CANDLE_GROUP))) {
                log.info("Consumer Group '{}' already exists.", Constant.KR_CANDLE_GROUP);
                return;
            }
        } catch (RedisCommandExecutionException e) {
            log.info("Error while fetching XInfoGroups: {}", e.getMessage());
            return;
        }
    
        try {
            streamCommands.xGroupCreate(streamKeyBytes, Constant.KR_CANDLE_GROUP, ReadOffset.latest());
            log.info("Created Consumer Group '{}'", Constant.KR_CANDLE_GROUP);
        } catch (RedisCommandExecutionException e) {
            if (e.getMessage().contains("BUSYGROUP")) {
                log.info("Consumer Group '{}' already exists.", Constant.KR_CANDLE_GROUP);
            } else {
                log.info(e.getMessage());
            }
        }
    }
}