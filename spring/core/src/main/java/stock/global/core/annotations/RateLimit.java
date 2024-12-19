package stock.global.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    int limit() default 1; // 분당 허용 요청 수
    int duration() default 60; // 제한 시간 (s)
    String message() default "Too many request";
}
