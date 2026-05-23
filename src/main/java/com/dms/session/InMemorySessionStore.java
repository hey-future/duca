package com.dms.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 内存存储 — 记录被 back-channel logout 失效的会话及其退出原因。
 */
@Component
public class InMemorySessionStore {

    /** sessionId → logout reason (manual_logout / concurrent_login) */
    private final Map<String, String> logoutReasons = new ConcurrentHashMap<>();

    public void markExpired(String sessionId, String reason) {
        logoutReasons.put(sessionId, reason != null ? reason : "manual_logout");
    }

    public String getReason(String sessionId) {
        return logoutReasons.get(sessionId);
    }

    public void remove(String sessionId) {
        logoutReasons.remove(sessionId);
    }
}
