package com.depromeet.service.authentication;

import com.depromeet.config.session.MemberSession;
import com.depromeet.constants.SessionConstants;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.external.google.oauth.GoogleOAuthApiCaller;
import com.depromeet.external.google.oauth.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.google.oauth.dto.response.GoogleUserProfileResponse;
import com.depromeet.service.authentication.dto.response.GoogleOAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService {

    private final HttpSession httpSession;
    private final GoogleOAuthApiCaller googleOAuthApiCaller;
    private final MemberRepository memberRepository;

    @Transactional
    public GoogleOAuthResponse getGoogleAuthentication(GoogleOAuthRequest request) {
        GoogleAccessTokenResponse tokenResponse = googleOAuthApiCaller.getGoogleAccessToken(request.getCode());
        GoogleUserProfileResponse profileResponse = googleOAuthApiCaller.getGoogleUserProfileInfo(tokenResponse.getAccessToken(), tokenResponse.getTokenType());

        Member member = memberRepository.findMemberByEmail(profileResponse.getEmail());
        if (member != null) {
            httpSession.setAttribute(SessionConstants.LOGIN_USER, MemberSession.of(member.getId()));
            return GoogleOAuthResponse.login(httpSession.getId());
        }
        return GoogleOAuthResponse.signUp(profileResponse.getEmail(), profileResponse.getName());
    }

}
