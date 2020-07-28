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
        GoogleAccessTokenResponse tokenResponse = googleExternalApiCaller.getGoogleAccessToken(request.getCode(), request.getRedirectUri());
        GoogleUserProfileResponse profileResponse = googleExternalApiCaller.getGoogleUserProfileInfo(tokenResponse.getAccessToken());

        Member member = memberRepository.findMemberByEmail(profileResponse.getEmail());
        // TODO 프론트쪽으로 콜백해서 추가 정보를 받을 경우 변경해야함.
        if (member != null) {
            return member.getId();
        }
        return memberRepository.save(profileResponse.toEntity()).getId();
    }

}
