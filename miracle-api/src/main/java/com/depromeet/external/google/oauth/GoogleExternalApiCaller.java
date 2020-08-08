package com.depromeet.external.google.oauth;

import com.depromeet.external.google.oauth.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.google.oauth.dto.response.GoogleUserProfileResponse;

public interface GoogleExternalApiCaller {

    GoogleAccessTokenResponse getGoogleAccessToken(String code);

    GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken, String tokenType);

}
