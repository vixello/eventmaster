package com.vixello.tickets.domain.controllers;

import com.vixello.tickets.domain.dtos.AuthResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    @GetMapping("/current-user")
    public ResponseEntity<AuthResponseDto> currentUser(Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()){
            return ResponseEntity.status(401).build();
        }


        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .toList();

        AuthResponseDto response = new AuthResponseDto(
                authentication.getName(),
                roles
        );

        return ResponseEntity.ok(response);
    }
}
