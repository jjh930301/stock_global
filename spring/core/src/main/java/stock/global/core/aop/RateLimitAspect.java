package stock.global.core.aop;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import stock.global.core.annotations.RateLimit;
import stock.global.core.models.ApiRes;

@Aspect
@Component
public class RateLimitAspect {

    private final Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = joinPoint.getSignature().toShortString();

        long currentTime = System.currentTimeMillis();
        long allowedTime = requestTimestamps.getOrDefault(key, 0L);

        if (currentTime < allowedTime) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                    ApiRes
                        .builder()
                        .messages(Arrays.asList(rateLimit.message()))
                        .status(403)
                        .build()
                );
        }

        requestTimestamps.put(key, currentTime + (rateLimit.duration() * 1000L));
        return joinPoint.proceed();
    }
}