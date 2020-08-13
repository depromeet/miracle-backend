package com.depromeet.service.authentication;


import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.external.google.oauth.GoogleOAuthApiCaller;
import com.depromeet.external.google.oauth.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.google.oauth.dto.response.GoogleUserProfileResponse;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.service.authentication.dto.response.GoogleOAuthResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GoogleOAuthServiceTest {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private MemberRepository memberRepository;

    private GoogleOAuthService oAuthService;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @BeforeEach
    void setUpService() {
        oAuthService = new GoogleOAuthService(httpSession, new StubGoogleOAuthApiCaller(), memberRepository);
    }

    private static class StubGoogleOAuthApiCaller implements GoogleOAuthApiCaller {
        @Override
        public GoogleAccessTokenResponse getGoogleAccessToken(String code) {
            return GoogleAccessTokenResponse.testBuilder()
                .accessToken("accessToken")
                .tokenType("tokenType")
                .build();
        }

        @Override
        public GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken, String tokenType) {
            return GoogleUserProfileResponse.testBuilder()
                .email("will.seungho@gmail.com")
                .name("강승호")
                .build();
        }
    }

    @Test
    void 구글_OAUTH_요청시_이미_존재하는_회원이_있으면_로그인이_진행된다() {
        // given
        String email = "will.seungho@gmail.com";
        memberRepository.save(MemberCreator.create(email));

        GoogleOAuthRequest request = GoogleOAuthRequest.testBuilder()
            .code("code")
            .build();

        // when
        GoogleOAuthResponse response = oAuthService.getGoogleAuthentication(request);

        // then
        assertGoogleOAuthResponse(response, GoogleOAuthResponse.OAuthType.LOGIN, httpSession.getId(), null, null);
    }

    @Test
    void 구글_OAUTH_요청시_기존에_존재하는_회원이_없으면_회원가입을_위한_정보가_반환된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";

        GoogleOAuthRequest request = GoogleOAuthRequest.testBuilder()
            .code("code")
            .build();

        // when
        GoogleOAuthResponse response = oAuthService.getGoogleAuthentication(request);

        // then
        assertGoogleOAuthResponse(response, GoogleOAuthResponse.OAuthType.SIGN_UP, null, email, name);
    }

    private void assertGoogleOAuthResponse(GoogleOAuthResponse response, GoogleOAuthResponse.OAuthType type, String sessionId, String email, String name) {
        assertThat(response.getType()).isEqualTo(type);
        assertThat(response.getSessionId()).isEqualTo(sessionId);
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getName()).isEqualTo(name);
    }

}
