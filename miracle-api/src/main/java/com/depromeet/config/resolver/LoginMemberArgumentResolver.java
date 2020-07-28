package com.depromeet.config.resolver;

import com.depromeet.config.session.MemberSession;
import com.depromeet.constants.SessionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.depromeet.constants.HeaderConstants.AUTHORIZATION_HEADER;
import static com.depromeet.constants.HeaderConstants.BEARER_TOKEN;

@RequiredArgsConstructor
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isLongType = parameter.getParameterType().equals(MemberSession.class);
        return hasAnnotation && isLongType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(AUTHORIZATION_HEADER);
        Session session = extractSessionFromHeader(header);
        return session.getAttribute(SessionConstants.LOGIN_USER);
    }

    private Session extractSessionFromHeader(String header) {
        validateAvailableHeader(header);
        Session session = sessionRepository.getSession(header.split(BEARER_TOKEN)[1]);
        if (session == null) {
            throw new IllegalArgumentException(String.format("잘못된 세션입니다 (%s)", header));
        }
        return session;
    }

    private void validateAvailableHeader(String header) {
        if (header == null) {
            throw new IllegalArgumentException("세션이 없습니다");
        }
        if (!header.startsWith(BEARER_TOKEN)) {
            throw new IllegalArgumentException(String.format("잘못된 세션입니다 (%s)", header));
        }
    }

}
