package com.depromeet.external.dto.component;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ToString
@Setter
@Getter
@ConfigurationProperties(prefix = "google.oauth")
@Component
@NoArgsConstructor
public class GoogleAccessTokenComponent {

    private String clientId;

    private String clientSecret;

    private String url;

    private String grantType;

}
