package com.sey.community.springboot.config.auth;

import com.sey.community.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @LoginUser 애너테이션 붙었나?
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) !=null;
        // 파라미터의 클래스 타입이 dto.SessionUser.class 인가?
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, 
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, 
                                  WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체 생성
        return httpSession.getAttribute("user");
    }
}
