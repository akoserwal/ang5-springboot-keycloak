package com.redhat.authapp.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ApplicationSecurityConfiguration extends ResourceServerConfigurerAdapter {
    private final TokenStore tokenStore;

    @Value("${keycloak.client.id}")
    private String keycloakClientId;

    @Autowired
    public ApplicationSecurityConfiguration(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // We're protecting a customers resource here.
        resources.resourceId(keycloakClientId).tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Disable CSRF, we're a microservice not a website.
        // Next configure the exception handling for authentication to return a 401 instead of a login page.
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(401))
                .and()
                // Authorization is required for all requests
                // Except for the OPTIONS request which requires anonymous access
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                // Use basic authentication headers because
                // JSON web tokens are transported in the authorization header
                .httpBasic()
                // Enable CORS
                .and()
                .cors();
    }
}
