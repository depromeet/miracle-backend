package com.depromeet.external.google.oauth.dto.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Getter
@Setter
@ConfigurationProperties(prefix = "google.profile")
@Component
public class GoogleUserProfileComponent {

    private String url;

}
