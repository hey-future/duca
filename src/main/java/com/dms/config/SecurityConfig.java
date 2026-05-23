package com.dms.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/logged-out", "/error",
                    "/duca/backChannelLogout", "/api/session/check",
                    "/api/auth/device", "/api/auth/device/poll",
                    "/api/auth/pkce/callback", "/api/auth/pkce/token",
                    "/api/auth/par/callback", "/api/auth/par/token",
                    "/api/auth/dpop/echo",
                    "/js/**", "/css/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/", true)
                .authorizationEndpoint(auth -> auth
                    .authorizationRequestResolver(nonceRemovingResolver())
                )
            )
            .logout(logout -> logout
                .logoutSuccessHandler(oidcLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(sessionExpiredStrategy())
            )
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    ajaxRequestMatcher()
                )
            );

        return http.build();
    }

    private OAuth2AuthorizationRequestResolver nonceRemovingResolver() {
        var delegate = new DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository,
            OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);
        return new OAuth2AuthorizationRequestResolver() {
            public OAuth2AuthorizationRequest resolve(HttpServletRequest req) {
                return strip(delegate.resolve(req));
            }
            public OAuth2AuthorizationRequest resolve(HttpServletRequest req, String id) {
                return strip(delegate.resolve(req, id));
            }
            private OAuth2AuthorizationRequest strip(OAuth2AuthorizationRequest r) {
                if (r == null) return null;
                var params = new HashMap<>(r.getAdditionalParameters());
                params.remove("nonce");
                var attrs = new HashMap<>(r.getAttributes());
                attrs.remove("nonce");
                return OAuth2AuthorizationRequest.authorizationCode()
                    .authorizationUri(r.getAuthorizationUri())
                    .clientId(r.getClientId())
                    .redirectUri(r.getRedirectUri())
                    .scopes(new LinkedHashSet<>(r.getScopes()))
                    .state(r.getState())
                    .additionalParameters(params)
                    .attributes(attrs)
                    .authorizationRequestUri(r.getAuthorizationRequestUri())
                    .build();
            }
        };
    }

    private RequestMatcher ajaxRequestMatcher() {
        return new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
    }

    private SessionInformationExpiredStrategy sessionExpiredStrategy() {
        return event -> {
            HttpServletResponse response = event.getResponse();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(
                "{\"code\":401,\"reason\":\"session_expired_by_concurrent_login\"," +
                "\"msg\":\"您的账号已在其他地方登录\"}");
        };
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler handler =
            new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        handler.setPostLogoutRedirectUri("{baseUrl}/logged-out");
        return handler;
    }
}
