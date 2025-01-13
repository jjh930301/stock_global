package stock.global.core.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import stock.global.core.constants.Constant;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.TokenInfo;

@Component
public class JwtUtil {
    
    public String createToken(
        Long id,
        String accountId,
        MemberTypeEnum type
    ) {
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

		String secretKeyEncodeBase64 = Encoders.BASE64.encode(Constant.JWT_SECRET.getBytes());
		byte[] keyBytes = Decoders.BASE64.decode(secretKeyEncodeBase64);
		Key key = Keys.hmacShaKeyFor(keyBytes);

        JwtBuilder jwtBuilder = Jwts.builder()
            .setClaims(new HashMap<>(){{
                put("id", id);
                put("accountId", accountId);
                put("type" , type.ordinal());
            }});
        final int oneDay = 60*1000*3600*24;
        jwtBuilder
            .setIssuer("needsss")
            .signWith(key , algorithm)
            .setExpiration(new Date(System.currentTimeMillis()+oneDay));
        

        return jwtBuilder.compact();
    }

    public TokenInfo verifyToken(String jwt) {
        try {
            Claims claim = Jwts.parserBuilder()
                .setSigningKey(Constant.JWT_SECRET.getBytes())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
            return TokenInfo.builder()
                .id(((Number) claim.get("id")).longValue())
                .accountId((String) claim.get("accountId"))
                .type((int) claim.get("type"))
                .build();       
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            throw new ApiException(e.getMessage() , HttpStatus.UNAUTHORIZED);
        }
        
	}
}