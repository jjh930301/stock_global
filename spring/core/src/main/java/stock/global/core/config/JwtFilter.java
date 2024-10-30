package stock.global.core.config;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stock.global.core.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    
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
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.split(" ")[1];
        request.setAttribute(
            "token", 
            this.jwtUtil.verifyToken(token)
        );
        filterChain.doFilter(request, response);
    }
}
