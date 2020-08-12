package com.depromeet.controller;

import com.depromeet.config.resolver.LoginMember;
import com.depromeet.config.session.MemberSession;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class InMemoryLoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final long memberId;

    public InMemoryLoginMemberArgumentResolver(long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(MemberSession.class);
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return new MemberSession(memberId);
    }
}
