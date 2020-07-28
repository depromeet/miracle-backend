package com.depromeet.external;

import com.depromeet.external.dto.component.GoogleAccessTokenComponent;
import com.depromeet.external.dto.component.GoogleUserProfileComponent;
import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleExternalApiCallerImpl implements GoogleExternalApiCaller {

    private final RestTemplate restTemplate;
    private final GoogleAccessTokenComponent googleAccessTokenComponent;
    private final GoogleUserProfileComponent googleUserProfileComponent;

    @Override
    public GoogleAccessTokenResponse getGoogleAccessToken(String code, String redirectUri) {
        return restTemplate.postForObject(googleAccessTokenComponent.getUrl(),
            createGoogleAccessTokenRequest(code, redirectUri), GoogleAccessTokenResponse.class);
    }

    private HttpEntity<MultiValueMap<String, String>> createGoogleAccessTokenRequest(String code, String redirectUri) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", googleAccessTokenComponent.getClientId());
        params.add("client_secret", googleAccessTokenComponent.getClientSecret());
        params.add("grant_type", googleAccessTokenComponent.getGrantType());
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        return new HttpEntity<>(params, httpHeaders);
    }

    @Override
    public GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken) {
        return restTemplate.exchange(googleUserProfileComponent.getUrl(), HttpMethod.GET, createGoogleProfileRequest(accessToken), GoogleUserProfileResponse.class).getBody();
    }

    private HttpEntity<Map<Object, Object>> createGoogleProfileRequest(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", String.format("Bearer %s", accessToken));
        return new HttpEntity<>(Collections.emptyMap(), httpHeaders);
    }

}
