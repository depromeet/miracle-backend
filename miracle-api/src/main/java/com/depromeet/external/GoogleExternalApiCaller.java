package com.depromeet.external;

import com.depromeet.external.dto.response.GoogleAccessTokenResponse;
import com.depromeet.external.dto.response.GoogleUserProfileResponse;

public interface GoogleExternalApiCaller {

    GoogleAccessTokenResponse getGoogleAccessToken(String code);

    GoogleUserProfileResponse getGoogleUserProfileInfo(String accessToken, String tokenType);

}
