package com.vixello.tickets.util;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.UUID;

public final class UserUtil {
    private UserUtil(){
    }

    public static UUID parseUserId(OidcUser oidcUser) {
        return UUID.fromString(oidcUser.getSubject());
    }

}
