package stock.global.core.config;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.ApiRes;
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
        try {
            String authorization = request.getHeader("Authorization");
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
        } catch(ApiException | ServletException | IOException e) {
            this.handleException(
                response, 
                new ApiException(e.getMessage() , HttpStatus.UNAUTHORIZED)
            );
        }   
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        
        ApiRes<?> errorResponse = ApiRes.builder()
            .payload(null)
            .messages(Arrays.asList(e.getMessage()))
            .status(HttpStatus.UNAUTHORIZED.value())
            .build();
        
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
