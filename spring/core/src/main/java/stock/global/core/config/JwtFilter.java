package stock.global.core.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends OncePerRequestFilter{
    
    @Override
    protected void doFilterInternal(
        @NonNull
        HttpServletRequest request, 
        @NonNull
        HttpServletResponse response, 
        @NonNull
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization= request.getHeader("Authorization");
        log.info(authorization);
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
