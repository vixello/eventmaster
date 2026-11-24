package com.vixello.tickets.domain.controllers;

import com.vixello.tickets.domain.dtos.PurchaseTicketTypeCartRequestDto;
import com.vixello.tickets.domain.dtos.PurchaseTicketResponseDto;
import com.vixello.tickets.domain.mappers.TicketMapper;
import com.vixello.tickets.services.TicketTypeService;
import com.vixello.tickets.util.UserUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;
    private final TicketMapper ticketMapper;

    @PostMapping(path = "/purchases")
    public ResponseEntity<List<PurchaseTicketResponseDto>> purchaseTicket(
            @AuthenticationPrincipal OidcUser oidcUser,
            @Valid @RequestBody PurchaseTicketTypeCartRequestDto requestDto
            ){

        UUID userId = UserUtil.parseUserId(oidcUser);
        List<PurchaseTicketResponseDto> tickets = ticketTypeService.purchaseTicketsCart(userId, requestDto)
                .stream().map(ticketMapper::toPurchaseTicketResponseDto)
                .toList();


        return new ResponseEntity<>(tickets, HttpStatus.CREATED);
    }
}
