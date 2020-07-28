package com.depromeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@EnableJpaAuditing
@EnableConfigurationProperties
@SpringBootApplication
public class MiracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiracleApplication.class, args);
    }

}
