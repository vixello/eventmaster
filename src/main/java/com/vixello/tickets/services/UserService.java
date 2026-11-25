package com.vixello.tickets.services;

import com.vixello.tickets.repositories.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.UUID;

public interface UserService {
    UUID getLocalUserId(OidcUser oidcUser);
}
