package stock.global.core.config;

import java.util.List;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.Filter;
import stock.global.core.util.JwtUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    private final TokenInterceptor tokenInterceptor;
    private final JwtUtil jwtUtil;
    public WebConfig(
        TokenInterceptor tokenInterceptor,
        JwtUtil jwtUtil
    ) {
        this.tokenInterceptor = tokenInterceptor;
        this.jwtUtil = jwtUtil;
    }
    @Bean
    public FilterRegistrationBean<Filter> jwtFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new JwtFilter(jwtUtil));
        return bean;
    }
    @Bean
    public TokenRoleResolver getTokenRoleResolver() {
        return new TokenRoleResolver();
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(getTokenRoleResolver());
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
