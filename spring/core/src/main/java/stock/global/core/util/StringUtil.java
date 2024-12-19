package stock.global.core.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class StringUtil {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateRandomString(int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 대문자와 소문자를 랜덤하게 선택
            String source = RANDOM.nextBoolean() ? UPPERCASE : LOWERCASE;
            result.append(source.charAt(RANDOM.nextInt(source.length())));
        }
        return result.toString();
    }
}
