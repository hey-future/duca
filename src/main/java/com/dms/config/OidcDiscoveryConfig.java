package com.dms.config;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "duca")
public class OidcDiscoveryConfig {

    private String clientId;
    private String clientSecret;
    private String discoveryUrl;

    public void setClientId(String clientId) { this.clientId = clientId; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }
    public void setDiscoveryUrl(String discoveryUrl) { this.discoveryUrl = discoveryUrl; }

    // ─── Exposed as beans for other components ──────────────

    @Bean
    public String ducaClientId() { return clientId; }

    @Bean
    public String ducaClientSecret() { return clientSecret; }

    @Bean("ducaDiscoveryMeta")
    public Map<String, Object> discoveryMeta(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();
        Map<String, Object> meta = restTemplate.getForObject(discoveryUrl, Map.class);
        if (meta == null) {
            throw new IllegalStateException("无法从 " + discoveryUrl + " 获取 OIDC 配置");
        }
        return meta;
    }

    @Bean
    @DependsOn("ducaDiscoveryMeta")
    public ClientRegistrationRepository clientRegistrationRepository(Map<String, Object> ducaDiscoveryMeta) {
        String authMethod = readAuthMethod(ducaDiscoveryMeta);
        String jwksUri = readString(ducaDiscoveryMeta, "jwks_uri")
            .replace("{clientId}", clientId).replace("{ClientId}", clientId);

        Map<String, Object> providerMetadata = new HashMap<>();
        if (ducaDiscoveryMeta.containsKey("end_session_endpoint")) {
            providerMetadata.put("end_session_endpoint",
                ducaDiscoveryMeta.get("end_session_endpoint").toString());
        }

        ClientRegistration registration = ClientRegistration
            .withRegistrationId("duca")
            .clientName("DUCA SSO")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
            .scope(readScopes(ducaDiscoveryMeta))
            .authorizationUri(readString(ducaDiscoveryMeta, "authorization_endpoint"))
            .tokenUri(readString(ducaDiscoveryMeta, "token_endpoint"))
            .userInfoUri(readString(ducaDiscoveryMeta, "userinfo_endpoint"))
            .jwkSetUri(jwksUri)
            .issuerUri(readString(ducaDiscoveryMeta, "issuer"))
            .userNameAttributeName("sub")
            .clientAuthenticationMethod(new ClientAuthenticationMethod(authMethod))
            .providerConfigurationMetadata(providerMetadata)
            .build();

        return new InMemoryClientRegistrationRepository(registration);
    }

    @Bean
    @DependsOn("ducaDiscoveryMeta")
    public ConfigurableJWTProcessor<SecurityContext> logoutTokenProcessor(
            Map<String, Object> ducaDiscoveryMeta) throws Exception {
        String jwksUri = readString(ducaDiscoveryMeta, "jwks_uri")
            .replace("{clientId}", clientId).replace("{ClientId}", clientId);
        ConfigurableJWTProcessor<SecurityContext> processor = new DefaultJWTProcessor<>();
        JWKSource<SecurityContext> jwkSource = new RemoteJWKSet<>(new URL(jwksUri));
        processor.setJWSKeySelector(
            new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource));
        return processor;
    }

    @Bean
    @DependsOn("ducaDiscoveryMeta")
    public String issuer(Map<String, Object> ducaDiscoveryMeta) {
        return readString(ducaDiscoveryMeta, "issuer");
    }

    @Bean
    @DependsOn("ducaDiscoveryMeta")
    public String jwksUri(Map<String, Object> ducaDiscoveryMeta) {
        return readString(ducaDiscoveryMeta, "jwks_uri")
            .replace("{clientId}", clientId).replace("{ClientId}", clientId);
    }

    // ─── Parsing helpers ──────────────────────────────────

    @SuppressWarnings("unchecked")
    private String[] readScopes(Map<String, Object> meta) {
        Object scopes = meta.get("scopes_supported");
        if (scopes instanceof List<?> list && !list.isEmpty()) {
            return list.stream()
                .filter(String.class::isInstance).map(String.class::cast)
                .toArray(String[]::new);
        }
        return new String[]{"openid", "profile", "email"};
    }

    @SuppressWarnings("unchecked")
    private String readAuthMethod(Map<String, Object> meta) {
        Object methods = meta.get("token_endpoint_auth_methods_supported");
        if (methods instanceof List<?> list && !list.isEmpty()) {
            String first = (String) list.get(0);
            if ("client_secret_post".equals(first)) return "client_secret_post";
            if ("client_secret_basic".equals(first)) return "client_secret_basic";
            if ("none".equals(first)) return "none";
        }
        Object single = meta.get("token_endpoint_auth_method_supported");
        if (single instanceof String s) return s;
        return "client_secret_basic";
    }

    private String readString(Map<String, Object> meta, String key) {
        Object value = meta.get(key);
        if (value == null) {
            throw new IllegalStateException("OIDC 配置中缺少 " + key);
        }
        return value.toString();
    }
}
