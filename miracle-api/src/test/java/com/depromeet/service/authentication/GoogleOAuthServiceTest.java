package com.depromeet.service.authentication;


import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.external.GoogleExternalApiCaller;
import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;
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
        oAuthService = new GoogleOAuthService(httpSession, new StubGoogleExternalApiCaller(), memberRepository);
    }

    private static class StubGoogleExternalApiCaller implements GoogleExternalApiCaller {
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
                .picture("picture")
                .build();
        }
    }

    @Test
    void 존재하는_회원이_있으면_로그인이_진행된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "호승강";
        String profileUrl = "profileUrl";
        memberRepository.save(MemberCreator.create(email, name, profileUrl));

        GoogleOAuthRequest request = GoogleOAuthRequest.testBuilder()
            .code("code")
            .build();

        // when
        GoogleOAuthResponse response = oAuthService.getGoogleAuthentication(request);

        // then
        assertGoogleOAuthResponse(response, GoogleOAuthResponse.OAuthType.LOGIN, httpSession.getId(), null, null, null);
    }

    @Test
    void 존재하는_회원이_없으면_회원가입을_위한_정보가_반환된다() {
        // given
        String email = "will.seungho@gmail.com";
        String name = "강승호";
        String profileUrl = "picture";

        GoogleOAuthRequest request = GoogleOAuthRequest.testBuilder()
            .code("code")
            .build();

        // when
        GoogleOAuthResponse response = oAuthService.getGoogleAuthentication(request);

        // then
        assertGoogleOAuthResponse(response, GoogleOAuthResponse.OAuthType.SIGN_UP, null, email, name, profileUrl);
    }

    private void assertGoogleOAuthResponse(GoogleOAuthResponse response, GoogleOAuthResponse.OAuthType type, String sessionId, String email, String name, String picture) {
        assertThat(response.getType()).isEqualTo(type);
        assertThat(response.getSessionId()).isEqualTo(sessionId);
        assertThat(response.getEmail()).isEqualTo(email);
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getProfileUrl()).isEqualTo(picture);
    }

}
