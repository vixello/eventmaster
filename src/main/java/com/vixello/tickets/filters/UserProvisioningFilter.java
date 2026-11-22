package com.vixello.tickets.filters;

import com.vixello.tickets.domain.entities.User;
import com.vixello.tickets.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication != null 
                && authentication.isAuthenticated() 
                && authentication.getPrincipal() instanceof OidcUser oidcUser){

            String subject = oidcUser.getSubject();

            if (subject == null || subject.isBlank()) {
                throw new IllegalStateException("OIDC subject (sub) is missing");
            }

            UUID keycloakId = UUID.fromString(subject);
            if(!userRepository.existsById(keycloakId)){
                User user = new User();

                user.setId(keycloakId);
                user.setName(oidcUser.getPreferredUsername());
                user.setEmail(oidcUser.getEmail());

                userRepository.save(user);
            }
        }

        filterChain.doFilter(request,response);
    }
}
