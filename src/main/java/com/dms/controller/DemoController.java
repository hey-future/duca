package com.dms.controller;

import com.dms.service.DucaApiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

/**
 * 演示页面所有 API — 用户信息、令牌操作、全部 11 种授权方式。
 */
@RestController
@RequestMapping("/api")
public class DemoController {

    private final DucaApiService duca;
    private final OAuth2AuthorizedClientService authorizedClientService;

    public DemoController(DucaApiService duca,
                          OAuth2AuthorizedClientService authorizedClientService) {
        this.duca = duca;
        this.authorizedClientService = authorizedClientService;
    }

    // ─── UserInfo ──────────────────────────────────────────

    @GetMapping("/userinfo")
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal OidcUser user) {
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "未登录"));
        String accessToken = getAccessToken(user);
        if (accessToken == null) return ResponseEntity.status(400).body(Map.of("error", "无 access_token"));
        return duca.getUserInfo(accessToken);
    }

    // ─── Token Operations ──────────────────────────────────

    @GetMapping("/token/raw")
    public ResponseEntity<?> rawTokens(@AuthenticationPrincipal OidcUser user) {
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "未登录"));
        return ResponseEntity.ok(Map.of(
                "id_token", user.getIdToken() != null ? user.getIdToken().getTokenValue() : null,
                "access_token", getAccessToken(user),
                "refresh_token", getRefreshToken(user)
        ));
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@AuthenticationPrincipal OidcUser user) {
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "未登录"));
        String refreshToken = getRefreshToken(user);
        if (refreshToken == null) return ResponseEntity.status(400).body(Map.of("error", "无 refresh_token"));
        return duca.refreshToken(refreshToken);
    }

    @PostMapping("/token/introspect")
    public ResponseEntity<?> introspectToken(@AuthenticationPrincipal OidcUser user,
                                             @RequestParam(defaultValue = "") String token) {
        String t = token.isBlank() ? getAccessToken(user) : token;
        if (t == null) return ResponseEntity.status(400).body(Map.of("error", "无 token"));
        return duca.introspectToken(t);
    }

    @PostMapping("/token/revoke")
    public ResponseEntity<?> revokeToken(@AuthenticationPrincipal OidcUser user,
                                         @RequestParam(defaultValue = "access_token") String tokenTypeHint) {
        String token = getAccessToken(user);
        if (token == null) return ResponseEntity.status(400).body(Map.of("error", "无 token"));
        return duca.revokeToken(token, tokenTypeHint);
    }

    // ─── 方式一/二: 当前登录用户信息 ──────────────────────

    @GetMapping("/auth/current")
    public ResponseEntity<?> currentAuth(@AuthenticationPrincipal OidcUser user) {
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "未登录"));
        return ResponseEntity.ok(Map.of(
                "subject", user.getSubject(),
                "name", user.getFullName(),
                "email", user.getEmail(),
                "issuer", user.getIssuer(),
                "idToken", user.getIdToken() != null ? user.getIdToken().getTokenValue() : "N/A",
                "accessToken", mask(getAccessToken(user)),
                "refreshToken", mask(getRefreshToken(user))
        ));
    }

    // ─── 方式一/二: PKCE ─────────────────────────────────────

    @PostMapping("/auth/pkce/token")
    public ResponseEntity<?> pkceToken(@RequestParam String code,
                                       @RequestParam String codeVerifier,
                                       @RequestParam(defaultValue = "http://localhost:8080/api/auth/pkce/callback") String redirectUri) {
        return duca.pkceTokenExchange(code, codeVerifier, redirectUri);
    }

    @GetMapping("/auth/pkce/callback")
    public String pkceCallback(@RequestParam(required = false) String code,
                               @RequestParam(required = false) String state,
                               @RequestParam(required = false) String error,
                               @RequestParam(required = false) String error_description,
                               jakarta.servlet.http.HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        var w = response.getWriter();
        w.write("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>PKCE 回调</title>");
        w.write("<style>body{font-family:system-ui,sans-serif;max-width:600px;margin:40px auto;padding:20px;background:#f9f9f9}");
        w.write(".card{background:#fff;border-radius:8px;padding:24px;box-shadow:0 2px 8px rgba(0,0,0,.1);margin-bottom:16px}");
        w.write("code{background:#f0f0f0;padding:2px 6px;border-radius:3px;word-break:break-all;font-size:13px}");
        w.write("input{width:100%;box-sizing:border-box;padding:8px 12px;border:1px solid #ddd;border-radius:4px;font-size:14px}");
        w.write("button{padding:10px 20px;border:none;border-radius:4px;cursor:pointer;font-size:14px}");
        w.write(".btn-primary{background:#4361ee;color:#fff}.btn-primary:hover{background:#3a56d4}");
        w.write(".response{margin-top:12px;padding:12px;border-radius:4px;background:#f0f0f0;white-space:pre-wrap;font-size:13px;font-family:monospace;max-height:300px;overflow:auto;display:none}");
        w.write("</style></head><body>");
        w.write("<div class='card'><h2>PKCE 授权回调</h2>");
        if (error != null) {
            w.write("<p style='color:red'>授权失败: <b>" + escapeHtml(error) + "</b> — " +
                    (error_description != null ? escapeHtml(error_description) : "") + "</p>");
        } else if (code != null) {
            w.write("<p style='color:green'>✓ 授权码获取成功</p>");
            w.write("<div class='form-group'><label>authorization_code</label><input id='authCode' value='" + escapeHtml(code) + "' readonly></div>");
            w.write("<div class='form-group' style='margin-top:8px'><label>code_verifier（从 sessionStorage 获取）</label><input id='codeVerifier' placeholder='如果此处为空，请回到演示页面重新生成 PKCE 参数'></div>");
            w.write("<button class='btn-primary' onclick='exchangeToken()'>用 code_verifier 换取令牌</button>");
            w.write("<div class='response' id='response'></div>");
            w.write("<script>");
            w.write("var verifier = sessionStorage.getItem('pkce_code_verifier');");
            w.write("if (verifier) document.getElementById('codeVerifier').value = verifier;");
            w.write("function exchangeToken() {");
            w.write("var code = document.getElementById('authCode').value;");
            w.write("var verifier = document.getElementById('codeVerifier').value;");
            w.write("if (!code || !verifier) { alert('缺少 authorization_code 或 code_verifier'); return; }");
            w.write("var respEl = document.getElementById('response');");
            w.write("respEl.style.display = 'block'; respEl.textContent = 'Exchanging...';");
            w.write("fetch('/api/auth/pkce/token', {");
            w.write("method:'POST',");
            w.write("headers:{'Content-Type':'application/x-www-form-urlencoded','X-Requested-With':'XMLHttpRequest'},");
            w.write("body:'code='+encodeURIComponent(code)+'&codeVerifier='+encodeURIComponent(verifier)+'&redirectUri='+encodeURIComponent('http://localhost:8080/api/auth/pkce/callback')");
            w.write("}).then(r=>r.json()).then(d=>{respEl.textContent=JSON.stringify(d,null,2);respEl.style.background=d.error?'#ffe0e0':'#e0ffe0';})");
            w.write(".catch(e=>{respEl.textContent='Error: '+e.message;respEl.style.background='#ffe0e0';});");
            w.write("}</script>");
        } else {
            w.write("<p>没有授权码返回。请从演示页面的 PKCE 标签页开始授权流程。</p>");
        }
        if (state != null) w.write("<p style='font-size:12px;color:#999'>state: " + escapeHtml(state) + "</p>");
        w.write("</div></body></html>");
        return null;
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    // ─── 方式三: 设备授权 ───────────────────────────────────

    @PostMapping("/auth/device")
    public ResponseEntity<?> deviceAuth() {
        return duca.deviceAuthorization();
    }

    @PostMapping("/auth/device/poll")
    public ResponseEntity<?> devicePoll(@RequestParam String deviceCode) {
        return duca.pollDeviceToken(deviceCode);
    }

    // ─── 方式四: 密码模式 ────────────────────────────────────

    @PostMapping("/auth/password")
    public ResponseEntity<?> passwordLogin(@RequestParam String username,
                                           @RequestParam String password) {
        return duca.passwordLogin(username, password);
    }

    // ─── 方式五: 手机验证码 ───────────────────────────────────

    /**
     * 获取图形验证码（基于 token，不依赖 session）
     * 返回 {code:200, data: {token, image}} 或 {code:500, msg:"..."}
     */
    @GetMapping("/duca/verifyCode")
    public ResponseEntity<?> verifyCode() {
        try {
            ResponseEntity<?> resp = duca.getVerifyCode();
            if (resp.getBody() instanceof Map) {
                return ResponseEntity.ok(resp.getBody());
            }
            return ResponseEntity.ok(Map.of("code", 500, "msg", "获取验证码失败"));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of("code", 500, "msg", e.getMessage()));
        }
    }

    /**
     * 发送短信验证码
     */
    @PostMapping("/duca/smsCode")
    public ResponseEntity<?> smsCode(@RequestParam String phone,
                                     @RequestParam String captcha,
                                     @RequestParam String captchaToken) {
        return duca.sendSmsCode(phone, captcha, captchaToken);
    }

    /**
     * 方式一：仅手机号登录（无验证码）
     */
    @PostMapping("/auth/phone/only")
    public ResponseEntity<?> phoneLoginOnly(@RequestParam String phone) {
        return duca.phoneLoginOnly(phone);
    }

    /**
     * 方式二：手机号+短信验证码登录
     */
    @PostMapping("/auth/phone")
    public ResponseEntity<?> phoneLogin(@RequestParam String phone,
                                        @RequestParam(required = false) String code) {
        if (code == null || code.isBlank()) {
            return duca.phoneLoginOnly(phone);
        }
        return duca.phoneLogin(phone, code);
    }

    // ─── 方式六: 微信扫码 ────────────────────────────────────

    @GetMapping("/auth/wechat/qrcode")
    public ResponseEntity<?> wechatQrcode(@RequestParam(defaultValue = "demo") String state) {
        return duca.getWechatQrcode(state);
    }

    @GetMapping("/auth/wechat/status")
    public ResponseEntity<?> wechatStatus(@RequestParam String uuid,
                                          @RequestParam String state) {
        return duca.getWechatStatus(uuid, state);
    }

    // ─── 方式七: 企业微信扫码 ─────────────────────────────────

    @GetMapping("/auth/workwx/qrcode")
    public ResponseEntity<?> workwxQrcode(@RequestParam(defaultValue = "demo") String state) {
        return duca.getWorkwxQrcode(state);
    }

    @GetMapping("/auth/workwx/status")
    public ResponseEntity<?> workwxStatus(@RequestParam String uuid,
                                          @RequestParam String state) {
        return duca.getWorkwxStatus(uuid, state);
    }

    // ─── 方式七-b: 微信服务号网页授权 ─────────────────────

    @GetMapping("/auth/wxoffical/qrcode")
    public ResponseEntity<?> wxofficalQrcode(@RequestParam(defaultValue = "demo") String state) {
        return duca.getWxOfficalLoginUrl(state);
    }

    // ─── 方式八: 第三方 UID ───────────────────────────────────

    @PostMapping("/auth/third-uid")
    public ResponseEntity<?> thirdUidLogin(@RequestParam String thirdUserId,
                                           @RequestParam String userName) {
        return duca.thirdUidLogin(thirdUserId, userName);
    }

    // ─── 方式九: DPoP（客户端 JS 演示） ─────────────────────

    @GetMapping("/auth/dpop/echo")
    public ResponseEntity<?> dpopEcho(HttpServletRequest request) {
        String dpopHeader = request.getHeader("DPoP");
        String authHeader = request.getHeader("Authorization");
        return ResponseEntity.ok(Map.of(
                "message", "DPoP 请求已收到",
                "received_dpop_header", dpopHeader != null ? dpopHeader : "(未提供)",
                "received_auth_header", authHeader != null ? authHeader.replaceAll("DPoP ", "DPoP ") : "(未提供)",
                "note", "服务端应验证: 1) DPoP Proof JWT 签名 2) jti 防重放 3) ath 指纹匹配 access_token 4) htm/htu 匹配当前请求"
        ));
    }

    @GetMapping("/auth/dpop/info")
    public ResponseEntity<?> dpopInfo() {
        return ResponseEntity.ok(Map.of(
                "description", "DPoP 将 access_token 绑定到客户端非对称密钥。每次请求携带 DPoP Proof JWT（包含公钥 jwk），服务端验证后绑定令牌到该公钥指纹。",
                "supported_algorithms", new String[]{"RS256", "RS384"},
                "note", "DPoP 需要客户端生成 RSA 密钥对并在每次请求时生成 DPoP Proof JWT。请在前端使用 Web Crypto API 实现。"
        ));
    }

    // ─── 方式十: PAR ─────────────────────────────────────────

    @PostMapping("/auth/par")
    public ResponseEntity<?> par(@RequestParam String redirectUri) {
        return duca.par(redirectUri);
    }

    @PostMapping("/auth/par/token")
    public ResponseEntity<?> parToken(@RequestParam String code,
                                      @RequestParam(defaultValue = "http://localhost:8080/api/auth/par/callback") String redirectUri) {
        return duca.parTokenExchange(code, redirectUri);
    }

    @GetMapping("/auth/par/callback")
    public String parCallback(@RequestParam(required = false) String code,
                              @RequestParam(required = false) String state,
                              @RequestParam(required = false) String error,
                              @RequestParam(required = false) String error_description,
                              jakarta.servlet.http.HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        var w = response.getWriter();
        w.write("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>PAR 回调</title>");
        w.write("<style>body{font-family:system-ui,sans-serif;max-width:600px;margin:40px auto;padding:20px;background:#f9f9f9}");
        w.write(".card{background:#fff;border-radius:8px;padding:24px;box-shadow:0 2px 8px rgba(0,0,0,.1);margin-bottom:16px}");
        w.write("input{width:100%;box-sizing:border-box;padding:8px 12px;border:1px solid #ddd;border-radius:4px;font-size:14px}");
        w.write("button{padding:10px 20px;border:none;border-radius:4px;cursor:pointer;font-size:14px}");
        w.write(".btn-primary{background:#4361ee;color:#fff}.btn-primary:hover{background:#3a56d4}");
        w.write(".response{margin-top:12px;padding:12px;border-radius:4px;background:#f0f0f0;white-space:pre-wrap;font-size:13px;font-family:monospace;max-height:300px;overflow:auto;display:none}");
        w.write("</style></head><body><div class='card'><h2>PAR 授权回调</h2>");
        if (error != null) {
            w.write("<p style='color:red'>授权失败: <b>" + escapeHtml(error) + "</b> — " +
                    (error_description != null ? escapeHtml(error_description) : "") + "</p>");
        } else if (code != null) {
            w.write("<p style='color:green'>✓ PAR 授权码获取成功</p>");
            w.write("<div class='form-group'><label>authorization_code</label><input id='authCode' value='" + escapeHtml(code) + "' readonly></div>");
            w.write("<button class='btn-primary' onclick='exchangeToken()' style='margin-top:8px'>用授权码换取令牌</button>");
            w.write("<div class='response' id='response'></div>");
            w.write("<script>");
            w.write("function exchangeToken() {");
            w.write("var code = document.getElementById('authCode').value;");
            w.write("var respEl = document.getElementById('response');");
            w.write("respEl.style.display = 'block'; respEl.textContent = 'Exchanging...';");
            w.write("fetch('/api/auth/par/token', {");
            w.write("method:'POST',");
            w.write("headers:{'Content-Type':'application/x-www-form-urlencoded','X-Requested-With':'XMLHttpRequest'},");
            w.write("body:'code='+encodeURIComponent(code)+'&redirectUri='+encodeURIComponent('http://localhost:8080/api/auth/par/callback')");
            w.write("}).then(r=>r.json()).then(d=>{respEl.textContent=JSON.stringify(d,null,2);respEl.style.background=d.error?'#ffe0e0':'#e0ffe0';})");
            w.write(".catch(e=>{respEl.textContent='Error: '+e.message;respEl.style.background='#ffe0e0';});");
            w.write("}</script>");
        } else {
            w.write("<p>没有授权码返回。请从演示页面的 PAR 标签页开始授权流程。</p>");
        }
        if (state != null) w.write("<p style='font-size:12px;color:#999'>state: " + escapeHtml(state) + "</p>");
        w.write("</div></body></html>");
        return null;
    }

    // ─── 方式十一: Token Exchange ───────────────────────────

    @PostMapping("/auth/token-exchange")
    public ResponseEntity<?> tokenExchange(@AuthenticationPrincipal OidcUser user,
                                           @RequestParam String audience,
                                           @RequestParam(defaultValue = "") String scope) {
        String subjectToken = getAccessToken(user);
        if (subjectToken == null) return ResponseEntity.status(400).body(Map.of("error", "无 access_token"));
        return duca.tokenExchange(subjectToken, audience, scope);
    }

    // ─── Open API 代理 ─────────────────────────────────────

    @PostMapping("/open/proxy")
    public ResponseEntity<?> openApiProxy(@AuthenticationPrincipal OidcUser user,
                                          @RequestBody Map<String, Object> proxyRequest) {
        if (user == null) return ResponseEntity.status(401).body(Map.of("error", "未登录"));
        String accessToken = getAccessToken(user);
        if (accessToken == null) return ResponseEntity.status(400).body(Map.of("error", "无 access_token"));
        Object methodObj = proxyRequest.get("method");
        Object pathObj = proxyRequest.get("path");
        String method = methodObj != null ? methodObj.toString() : null;
        String path = pathObj != null ? pathObj.toString() : null;
        String requestBody = proxyRequest.get("body") != null ? proxyRequest.get("body").toString() : null;
        if (method == null || method.isBlank() || path == null || path.isBlank()) {
            return ResponseEntity.status(400).body(Map.of("error", "缺少 method 或 path 参数"));
        }
        return duca.openApiProxy(method, path, accessToken, requestBody);
    }

    // ─── 服务发现信息 ─────────────────────────────────────

    @GetMapping("/duca/discovery")
    public ResponseEntity<?> discovery() {
        return ResponseEntity.ok(duca.getDiscoveryMeta());
    }

    // ─── Helpers ──────────────────────────────────────────

    private String getAccessToken(OidcUser user) {
        if (user == null) return null;
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient("duca", user.getName());
        return client != null && client.getAccessToken() != null
                ? client.getAccessToken().getTokenValue() : null;
    }

    private String getRefreshToken(OidcUser user) {
        if (user == null) return null;
        OAuth2AuthorizedClient client =
                authorizedClientService.loadAuthorizedClient("duca", user.getName());
        return client != null && client.getRefreshToken() != null
                ? client.getRefreshToken().getTokenValue() : null;
    }

    private String mask(String token) {
        if (token == null) return "N/A";
        if (token.length() <= 20) return token.substring(0, 4) + "***";
        return token.substring(0, 12) + "..." + token.substring(token.length() - 8);
    }
}
