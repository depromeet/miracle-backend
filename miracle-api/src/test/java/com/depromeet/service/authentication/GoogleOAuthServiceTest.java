package com.depromeet.service.authentication;


import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberCreator;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.external.GoogleExternalApiCaller;
import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;
import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GoogleOAuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    private GoogleOAuthService oAuthService;

    @AfterEach
    void cleanUp() {
        memberRepository.deleteAll();
    }

    @BeforeEach
    void setUpService() {
        oAuthService = new GoogleOAuthService(new StubGoogleExternalApiCaller(), memberRepository);
    }

    private static class StubGoogleExternalApiCaller implements GoogleExternalApiCaller {
        @Override
        public GoogleAccessTokenResponse getGoogleAccessToken(String code, String redirectUri) {
            return GoogleAccessTokenResponse.testBuilder()
                .accessToken("accessToken")
                .build();
        }

        @Override
        public GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken) {
            return GoogleUserProfileResponse.testBuilder()
                .email("will.seungho@gmail.com")
                .name("강승호")
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
            .redirectUri("redirectUri")
            .build();

        // when
        oAuthService.getGoogleAuthentication(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMember(members.get(0), email, name, profileUrl);
    }

    @Test
    void 존재하는_회원이_없으면_회원가입이_진행된다() {
        // given
        GoogleOAuthRequest request = GoogleOAuthRequest.testBuilder()
            .code("code")
            .redirectUri("redirectUri")
            .build();

        // when
        oAuthService.getGoogleAuthentication(request);

        // then
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
        assertMember(members.get(0), "will.seungho@gmail.com", "강승호", null);
    }

    private void assertMember(Member member, String email, String name, String profileUrl) {
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getProfileUrl()).isEqualTo(profileUrl);
    }

}
