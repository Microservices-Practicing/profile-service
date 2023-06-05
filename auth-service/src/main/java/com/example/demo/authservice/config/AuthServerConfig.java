package com.example.demo.authservice.config;

import com.example.demo.authservice.utils.Jwks;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

@Configuration(proxyBeanMethods = false)
public class AuthServerConfig {

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    //        RegisteredClient registeredClient =
    // RegisteredClient.withId(UUID.randomUUID().toString())
    //            .clientId("messaging-client")
    //            .clientSecret("{noop}secret")
    //            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
    //            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
    //            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
    //            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
    //            .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
    //            .redirectUri("http://127.0.0.1:8080/authorized")
    //            .scope(OidcScopes.OPENID)
    //            .scope(OidcScopes.PROFILE)
    ////            .scope("message.read")
    ////            .scope("message.write")
    ////
    // .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
    //            .build();
    // Save registered client in db as if in-memory
    //        JdbcRegisteredClientRepository registeredClientRepository = new
    // JdbcRegisteredClientRepository(jdbcTemplate);
    //        registeredClientRepository.save(registeredClient);
    return new JdbcRegisteredClientRepository(jdbcTemplate);
  }

  @Bean
  public OAuth2AuthorizationService authorizationService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public OAuth2AuthorizationConsentService authorizationConsentService(
      JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = Jwks.generateRsa();
    JWKSet jwkSet = new JWKSet(rsaKey);
    return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
  }

  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }
}
