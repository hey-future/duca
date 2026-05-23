package com.dms.controller;

import com.dms.session.InMemorySessionStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 前端轮询检测本地会话是否因 back-channel logout 被失效。
 */
@RestController
public class SessionCheckController {

    private final InMemorySessionStore sessionStore;

    public SessionCheckController(InMemorySessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @GetMapping("/api/session/check")
    public Map<String, Object> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Map.of(
                "code", 401,
                "reason", "session_expired_by_concurrent_login",
                "msg", "会话已不存在"
            );
        }

        String reason = sessionStore.getReason(session.getId());
        if (reason != null) {
            boolean concurrent = "concurrent_login".equals(reason);
            return Map.of(
                "code", 401,
                "reason", "session_expired_by_concurrent_login",
                "logout_type", reason,
                "msg", concurrent ? "您的账号已在其他地方登录" : "您已退出登录"
            );
        }

        return Map.of("code", 200, "data", Map.of("status", "active"));
    }
}
