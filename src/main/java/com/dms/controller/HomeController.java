package com.dms.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    private final Map<String, Object> discoveryMeta;
    private final String clientId;

    public HomeController(@Qualifier("ducaDiscoveryMeta") Map<String, Object> discoveryMeta,
                          @Qualifier("ducaClientId") String clientId) {
        this.discoveryMeta = discoveryMeta;
        this.clientId = clientId;
    }

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("authEndpoint", discoveryMeta.get("authorization_endpoint"));
        model.addAttribute("clientId", clientId);
        model.addAttribute("issuer", discoveryMeta.get("issuer"));
        return "home";
    }

    @GetMapping("/logged-out")
    public String loggedOut() {
        return "logged-out";
    }
}
