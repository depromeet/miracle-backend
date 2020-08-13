package com.depromeet.external.google.oauth;

import com.depromeet.external.google.oauth.dto.component.GoogleAccessTokenComponent;
import com.depromeet.external.google.oauth.dto.component.GoogleUserProfileComponent;
import com.depromeet.external.google.oauth.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.google.oauth.dto.response.GoogleUserProfileResponse;
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
public class GoogleOAuthApiCallerImpl implements GoogleOAuthApiCaller {

    private final RestTemplate restTemplate;
    private final GoogleAccessTokenComponent googleAccessTokenComponent;
    private final GoogleUserProfileComponent googleUserProfileComponent;

    @Override
    public GoogleAccessTokenResponse getGoogleAccessToken(String code) {
        return restTemplate.postForObject(googleAccessTokenComponent.getUrl(),
            createGoogleAccessTokenRequest(code), GoogleAccessTokenResponse.class);
    }

    private HttpEntity<MultiValueMap<String, String>> createGoogleAccessTokenRequest(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", googleAccessTokenComponent.getClientId());
        params.add("client_secret", googleAccessTokenComponent.getClientSecret());
        params.add("grant_type", googleAccessTokenComponent.getGrantType());
        params.add("redirect_uri", googleAccessTokenComponent.getRedirectUri());
        params.add("code", code);

        return new HttpEntity<>(params, httpHeaders);
    }

    @Override
    public GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken, String tokenType) {
        return restTemplate.exchange(googleUserProfileComponent.getUrl(), HttpMethod.GET, createGoogleProfileRequest(accessToken, tokenType), GoogleUserProfileResponse.class).getBody();
    }

    private HttpEntity<Map<Object, Object>> createGoogleProfileRequest(String accessToken, String tokenType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("%s %s", tokenType, accessToken));
        return new HttpEntity<>(Collections.emptyMap(), httpHeaders);
    }

}
