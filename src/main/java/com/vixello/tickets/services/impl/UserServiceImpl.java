package com.vixello.tickets.services.impl;

import com.vixello.tickets.domain.entities.User;
import com.vixello.tickets.exceptions.UserNotFoundException;
import com.vixello.tickets.repositories.UserRepository;
import com.vixello.tickets.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UUID getLocalUserId(OidcUser oidcUser) {
        return userRepository.findByKeycloakId(oidcUser.getSubject())
                .map(User::getId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
