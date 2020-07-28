package com.depromeet.external;

import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;

public interface GoogleExternalApiCaller {

    GoogleAccessTokenResponse getGoogleAccessToken(String code, String redirectUri);

    GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken);

}
