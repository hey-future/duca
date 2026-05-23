package com.dms.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Map;

@Service
public class DucaApiService {

    private final RestTemplate restTemplate;
    private final String issuer;
    private final String clientId;
    private final String clientSecret;
    private final Map<String, Object> discoveryMeta;
    private final String baseUrl;

    public DucaApiService(RestTemplateBuilder builder,
                          @Qualifier("issuer") String issuer,
                          @Qualifier("ducaClientId") String clientId,
                          @Qualifier("ducaClientSecret") String clientSecret,
                          @Qualifier("ducaDiscoveryMeta") Map<String, Object> discoveryMeta) {
        this.restTemplate = builder
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(30))
            .build();
        this.issuer = issuer;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.discoveryMeta = discoveryMeta;
        this.baseUrl = issuer != null ? issuer : "";
    }

    // ─── UserInfo ───────────────────────────────────────────

    public ResponseEntity<?> getUserInfo(String accessToken) {
        String url = getEndpoint("userinfo_endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        return restTemplate.exchange(url, HttpMethod.GET,
            new HttpEntity<>(headers), Map.class);
    }

    // ─── Token Operations ────────────────────────────────────

    public ResponseEntity<?> refreshToken(String refreshToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        return postToken(body);
    }

    public ResponseEntity<?> introspectToken(String token) {
        String url = getEndpoint("introspection_endpoint");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        return postForm(url, body);
    }

    public ResponseEntity<?> revokeToken(String token, String tokenTypeHint) {
        String url = getEndpoint("revocation_endpoint");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("token", token);
        body.add("token_type_hint", tokenTypeHint != null ? tokenTypeHint : "access_token");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        return postForm(url, body);
    }

    // ─── Auth Methods ───────────────────────────────────────

    /** 密码模式 */
    public ResponseEntity<?> passwordLogin(String username, String password) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        body.add("scope", "openid profile");
        return postToken(body);
    }

    // ─── 手机验证码 ──────────────────────────────────────

    /** 获取图形验证码（基于 token，不依赖 session），返回 {token, image} */
    public ResponseEntity<?> getVerifyCode() {
        String url = baseUrl + "/verifyCodeToken";
        return restTemplate.getForEntity(url, Map.class);
    }

    /** 发送手机短信验证码（需先通过 /verifyCodeToken 获取 captchaToken） */
    public ResponseEntity<?> sendSmsCode(String phone, String captcha, String captchaToken) {
        String url = baseUrl + "/smsCode";
        Map<String, Object> body = new java.util.LinkedHashMap<>();
        body.put("phone", phone);
        body.put("captcha", captcha);
        body.put("token", captchaToken);
        body.put("clientId", clientId);
        return postJson(url, body);
    }

    /** 手机验证码登录 — 方式二：手机号+短信验证码 */
    public ResponseEntity<?> phoneLogin(String phone, String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "phone");
        body.add("phone", phone);
        body.add("code", code);
        body.add("client_id",clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "openid profile");

        return postToken(body);
    }

    /** 手机验证码登录 — 方式一：仅手机号(无验证码) */
    public ResponseEntity<?> phoneLoginOnly(String phone) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "phone");
        body.add("phone", phone);
        body.add("client_id",clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "openid profile");
        return postToken(body);
    }

    /** 设备授权 — 获取 device_code */
    public ResponseEntity<?> deviceAuthorization() {
        String url = getEndpoint("device_authorization_endpoint");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("scope", "openid profile");
        return postForm(url, body);
    }

    /** 设备授权 — 轮询令牌 */
    public ResponseEntity<?> pollDeviceToken(String deviceCode) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "urn:ietf:params:oauth:grant-type:device_code");
        body.add("device_code", deviceCode);
        body.add("client_id", clientId);
        return postToken(body);
    }

    /** 第三方 UID 登录（thirdUserId + userName 均为必填） */
    public ResponseEntity<?> thirdUidLogin(String thirdUserId, String userName) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "thirdUid");
        body.add("thirdUserId", thirdUserId);
        body.add("userName", userName);
        body.add("name","hiii");
        body.add("avatar","aa");
        body.add("scope", "openid profile");
        return postToken(body);
    }

    /** Token Exchange（令牌交换） */
    public ResponseEntity<?> tokenExchange(String subjectToken, String audience, String scope) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "urn:ietf:params:oauth:grant-type:token-exchange");
        body.add("subject_token", subjectToken);
        body.add("subject_token_type", "urn:ietf:params:oauth:token-type:access_token");
        body.add("requested_token_type", "urn:ietf:params:oauth:token-type:access_token");
        if (audience != null && !audience.isBlank()) body.add("audience", audience);
        if (scope != null && !scope.isBlank()) body.add("scope", scope);
        return postToken(body);
    }

    /** PKCE — 用 authorization_code + code_verifier 换令牌 */
    public ResponseEntity<?> pkceTokenExchange(String code, String codeVerifier, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("code_verifier", codeVerifier);
        body.add("redirect_uri", redirectUri);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = getEndpoint("token_endpoint");
        try {
            return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", e.getStatusText(),
                    "error_description", e.getResponseBodyAsString()));
        }
    }

    /** PAR — 推送授权请求 */
    public ResponseEntity<?> par(String redirectUri) {
        String url = getEndpoint("par_endpoint", baseUrl + "/oauth2/par");
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("response_type", "code");
        body.add("redirect_uri", redirectUri != null ? redirectUri : "http://localhost:8080/api/auth/par/callback");
        body.add("scope", "openid profile");
        body.add("state", "par-state-" + System.currentTimeMillis());
        return postForm(url, body);
    }

    /** PAR — 用 authorization_code 换令牌（类似标准 code flow） */
    public ResponseEntity<?> parTokenExchange(String code, String redirectUri) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri != null ? redirectUri : "http://localhost:8080/api/auth/par/callback");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        return postForm(getEndpoint("token_endpoint"), body);
    }

    /** 微信扫码 — 获取二维码 URL */
    public ResponseEntity<?> getWechatQrcode(String state) {
        String url = baseUrl + "/wechat/login/" + clientId + "?state=" + state;
        return restTemplate.getForEntity(url, Map.class);
    }

    /** 微信扫码 — DUCA 无轮询端点，扫码后由微信回调 DUCA 完成认证 */
    public ResponseEntity<?> getWechatStatus(String uuid, String state) {
        return ResponseEntity.ok(Map.of(
            "note", "微信扫码登录无轮询接口。用户扫码后微信回调 DUCA，DUCA 完成认证并重定向。",
            "uuid", uuid,
            "state", state
        ));
    }

    /** 企业微信扫码 — 获取二维码 URL */
    public ResponseEntity<?> getWorkwxQrcode(String state) {
        String url = baseUrl + "/workwx/qrCode?state=" + state;
        return restTemplate.getForEntity(url, Map.class);
    }

    /** 企业微信扫码 — 无轮询，扫码后由企业微信回调 */
    public ResponseEntity<?> getWorkwxStatus(String uuid, String state) {
        return ResponseEntity.ok(Map.of(
            "note", "企业微信扫码登录无轮询接口。用户扫码后企业微信回调 DUCA 完成认证。",
            "uuid", uuid,
            "state", state
        ));
    }

    /** Open API 通用代理 — 用 access_token 调用 DUCA 开放接口 */
    public ResponseEntity<?> openApiProxy(String method, String path, String accessToken, String requestBody) {
        // Reject unreplaced template placeholders
        if (path.contains("{") || path.contains("}")) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "invalid_path",
                    "error_description", "路径包含未替换的占位符，请将 {uuid}/{id}/{clientId} 替换为实际值"));
        }
        String url = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        boolean hasBody = requestBody != null && !requestBody.isBlank();
        if (hasBody) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        try {
            HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
            HttpEntity<String> entity = hasBody
                ? new HttpEntity<>(requestBody, headers)
                : new HttpEntity<>(headers);
            return restTemplate.exchange(url, httpMethod, entity, Map.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", e.getStatusText(),
                    "error_description", e.getResponseBodyAsString()));
        }
    }

    /** 微信服务号 — 网页授权登录 URL（仅限微信内浏览器打开） */
    public ResponseEntity<?> getWxOfficalLoginUrl(String state) {
        String url = baseUrl + "/wxOffical/login/" + clientId + "?state=" + state;
        return restTemplate.getForEntity(url, Map.class);
    }

    /** 获取完整 discovery 元数据（供前端展示） */
    public Map<String, Object> getDiscoveryMeta() {
        return discoveryMeta;
    }

    // ─── Internal Helpers ────────────────────────────────────

    private ResponseEntity<?> postToken(MultiValueMap<String, String> body) {
        body.addIfAbsent("client_id", clientId);
        body.addIfAbsent("client_secret", clientSecret);
        String url = getEndpoint("token_endpoint");
        return postForm(url, body);
    }

    private ResponseEntity<?> postJson(String url, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", e.getStatusText(),
                    "error_description", e.getResponseBodyAsString()));
        }
    }

    private ResponseEntity<?> postForm(String url, MultiValueMap<String, String> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try {
            return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", e.getStatusText(),
                    "error_description", e.getResponseBodyAsString()));
        }
    }

    private ResponseEntity<?> postWithBasicAuth(MultiValueMap<String, String> body) {
        String url = getEndpoint("token_endpoint");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth(clientId, clientSecret);
        try {
            return restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(body, headers), Map.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode())
                .body(Map.of("error", e.getStatusText(),
                    "error_description", e.getResponseBodyAsString()));
        }
    }

    private String getEndpoint(String key) {
        return getEndpoint(key, baseUrl + "/oauth2/token");
    }

    private String getEndpoint(String key, String fallback) {
        Object val = discoveryMeta.get(key);
        return val != null ? val.toString() : fallback;
    }
}
