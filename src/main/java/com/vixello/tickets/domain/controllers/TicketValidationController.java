package com.vixello.tickets.domain.controllers;

import com.vixello.tickets.domain.dtos.TicketValidationRequestDto;
import com.vixello.tickets.domain.dtos.TicketValidationResponseDto;
import com.vixello.tickets.domain.entities.TicketValidation;
import com.vixello.tickets.domain.entities.TicketValidationMethod;
import com.vixello.tickets.domain.mappers.TicketValidationMapper;
import com.vixello.tickets.services.TicketValidationService;
import com.vixello.tickets.services.UserService;
import com.vixello.tickets.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping(path = "/api/v1/ticket-validation")
@RequiredArgsConstructor
public class TicketValidationController {
    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<TicketValidationResponseDto> validateTicket(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RequestBody TicketValidationRequestDto ticketValidationRequestDto
            ){

        TicketValidationMethod method = ticketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        if(TicketValidationMethod.MANUAL.equals(method)){
            ticketValidation = ticketValidationService.validateTicketManually(
                    ticketValidationRequestDto.getId()
            );
        }
        else{
            ticketValidation = ticketValidationService.validateTicketByQrCode(
                    ticketValidationRequestDto.getId()
            );
        }
        return ResponseEntity.ok(ticketValidationMapper.toTicketValidationResponseDto(ticketValidation));
    }
}
