package com.depromeet.service.authentication;

import com.depromeet.service.authentication.dto.request.GoogleOAuthRequest;
import com.depromeet.domain.member.Member;
import com.depromeet.domain.member.MemberRepository;
import com.depromeet.external.GoogleExternalApiCaller;
import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GoogleOAuthService {

    private final GoogleExternalApiCaller googleExternalApiCaller;
    private final MemberRepository memberRepository;

    @Transactional
    public Long getGoogleAuthentication(GoogleOAuthRequest request) {
        GoogleAccessTokenResponse tokenResponse = googleExternalApiCaller.getGoogleAccessToken(request.getCode());
        GoogleUserProfileResponse profileResponse = googleExternalApiCaller.getGoogleUserProfileInfo(tokenResponse.getAccessToken(), tokenResponse.getTokenType());

        Member member = memberRepository.findMemberByEmail(profileResponse.getEmail());
        // 회원가입 플로우 결정되면 변경해야함.
        if (member != null) {
            return member.getId();
        }
        return memberRepository.save(profileResponse.toEntity()).getId();
    }

}
