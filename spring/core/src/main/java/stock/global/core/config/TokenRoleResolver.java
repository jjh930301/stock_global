package stock.global.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import stock.global.core.annotations.TokenRole;
import stock.global.core.enums.MemberTypeEnum;
import stock.global.core.exceptions.ApiException;
import stock.global.core.models.TokenInfo;

@Configuration
public class TokenRoleResolver implements HandlerMethodArgumentResolver{

    @Override
    public Object resolveArgument(
        @NonNull
        MethodParameter parameter, 
        @Nullable
        ModelAndViewContainer mavContainer, 
        @NonNull
        NativeWebRequest webRequest, 
        @Nullable
        WebDataBinderFactory binderFactory
    ) throws Exception {
        TokenRole role = parameter.getParameterAnnotation(TokenRole.class);
        if(role == null) throw new IllegalArgumentException("TokenRole annotation is missing");
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        TokenInfo info = (TokenInfo) request.getAttribute("token");
        
        if(info == null) 
            throw new ApiException("Unauthorized" , HttpStatus.UNAUTHORIZED);
        if(info.getType() != MemberTypeEnum.ADMIN.ordinal())
            return info;
        if(role.value().ordinal() != info.getType())
            throw new ApiException("Forbidden" , HttpStatus.FORBIDDEN);
        return info;
    }

    @Override
    public boolean supportsParameter(@NonNull MethodParameter parameter) {
        return parameter.hasParameterAnnotation(TokenRole.class);
    }  
    
}
