package com.redhat.authapp.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.jwk.JwkTokenStore;

@Configuration
public class JsonWebTokenConfiguration {

    @Bean
    public TokenStore tokenStore(@Value("${keycloak.jwks.url}")String keyUrl) {
        return new JwkTokenStore(keyUrl);
    }
}
