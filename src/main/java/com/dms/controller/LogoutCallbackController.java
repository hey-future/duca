package com.dms.controller;

import com.dms.session.InMemorySessionStore;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Back-Channel Logout 回调端点。
 * DUCA 服务器在用户退出(主动或被挤掉)时，通过服务端直连 POST 到此端点通知客户端。
 */
@RestController
public class LogoutCallbackController {

    private static final Logger log = LoggerFactory.getLogger(LogoutCallbackController.class);

    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;
    private final SessionRegistry sessionRegistry;
    private final InMemorySessionStore sessionStore;
    private final String issuer;

    public LogoutCallbackController(ConfigurableJWTProcessor<SecurityContext> jwtProcessor,
                                    SessionRegistry sessionRegistry,
                                    InMemorySessionStore sessionStore,
                                    @Qualifier("issuer") String issuer) {
        this.jwtProcessor = jwtProcessor;
        this.sessionRegistry = sessionRegistry;
        this.sessionStore = sessionStore;
        this.issuer = issuer;
    }

    @PostMapping("/duca/backChannelLogout")
    public void handleBackChannelLogout(HttpServletRequest request) {
        String logoutToken = request.getParameter("logout_token");
        if (logoutToken == null || logoutToken.isBlank()) {
            log.warn("Back-channel logout: 缺少 logout_token 参数");
            return;
        }

        try {
            JWTClaimsSet claims = jwtProcessor.process(logoutToken, null);
            String sub = claims.getSubject();
            String sid = claims.getStringClaim("sid");
            String reason = claims.getStringClaim("reason");

            log.info("Back-channel logout: user={}, sid={}, reason={}", sub, sid, reason);

            // 找到该用户的所有本地会话并立即失效
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(sub, false);
            for (SessionInformation si : sessions) {
                sessionStore.markExpired(si.getSessionId(), reason);
                si.expireNow();
                log.info("已失效本地会话: sessionId={}, user={}", si.getSessionId(), sub);
            }

            if (sessions.isEmpty()) {
                log.info("用户 {} 无活跃本地会话，跳过", sub);
            }
        } catch (Exception e) {
            log.error("处理 back-channel logout 失败: {}", e.getMessage(), e);
        }
    }

    /** 模拟 Back-Channel 退出（仅用于 Demo 演示） */
    @PostMapping("/api/logout/simulate-backchannel")
    public Map<String, Object> simulateBackChannel(
            @AuthenticationPrincipal OidcUser user,
            @RequestParam(defaultValue = "concurrent_login") String reason,
            HttpServletRequest request) {
        if (user == null) {
            return Map.of("code", 401, "msg", "未登录");
        }
        String sub = user.getSubject();
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(sub, false);
        int count = 0;
        for (SessionInformation si : sessions) {
            sessionStore.markExpired(si.getSessionId(), reason);
            si.expireNow();
            count++;
        }
        // Also expire current session immediately
        HttpSession session = request.getSession(false);
        if (session != null) {
            sessionStore.markExpired(session.getId(), reason);
            session.invalidate();
        }
        log.info("Demo: 模拟 Back-Channel Logout, user={}, reason={}, expired={} sessions", sub, reason, count);
        return Map.of("code", 200, "msg", "已模拟 Back-Channel 退出",
            "expired_sessions", count, "reason", reason,
            "hint", "刷新页面或下一次 session 轮询将收到 401");
    }

    /** 获取当前会话的退出原因（供前端展示） */
    @GetMapping("/api/logout/reason")
    public Map<String, Object> getLogoutReason(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Map.of("code", 401, "msg", "无会话");
        }
        String reason = sessionStore.getReason(session.getId());
        return Map.of("code", 200,
            "sessionId", session.getId(),
            "expired", reason != null,
            "reason", reason != null ? reason : "none",
            "reasonLabel", reason == null ? "正常" :
                "concurrent_login".equals(reason) ? "被挤掉 (concurrent_login)" : "主动退出 (manual_logout)");
    }
}
