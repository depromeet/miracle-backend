package com.depromeet.config.resolver;

import com.depromeet.config.session.MemberSession;
import com.depromeet.constants.SessionConstants;
import com.deprommet.exception.InvalidSessionException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String BEARER_TOKEN = "Bearer ";

    private final SessionRepository sessionRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(LoginMember.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(MemberSession.class);
        return hasAnnotation && isMatchType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        Session session = extractSessionFromHeader(header);
        return session.getAttribute(SessionConstants.LOGIN_USER);
    }

    private Session extractSessionFromHeader(String header) {
        validateAvailableHeader(header);
        Session session = sessionRepository.getSession(header.split(BEARER_TOKEN)[1]);
        if (session == null) {
            throw new InvalidSessionException(String.format("잘못된 세션입니다 (%s)", header), "세션이 만료되었습니다. 다시 로그인 해주세요.");
        }
        return session;
    }

    private void validateAvailableHeader(String header) {
        if (header == null) {
            throw new InvalidSessionException("세션이 없습니다", "세션이 만료되었습니다. 다시 로그인 해주세요.");
        }
        if (!header.startsWith(BEARER_TOKEN)) {
            throw new InvalidSessionException(String.format("잘못된 세션입니다 (%s)", header), "세션이 만료되었습니다. 다시 로그인 해주세요.");
        }
    }

}
