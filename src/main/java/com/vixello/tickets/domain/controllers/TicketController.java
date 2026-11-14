package com.vixello.tickets.domain.controllers;

import com.vixello.tickets.domain.dtos.GetTicketResponseDto;
import com.vixello.tickets.domain.dtos.ListTicketResponseDto;
import com.vixello.tickets.domain.mappers.TicketMapper;
import com.vixello.tickets.services.QrCodeService;
import com.vixello.tickets.services.TicketService;
import com.vixello.tickets.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final TicketMapper ticketMapper;
    private final QrCodeService qrCodeServicer;

    @GetMapping
    public Page<ListTicketResponseDto> listTickets(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
            ){
        UUID userId = JwtUtil.parseUserId(jwt);

        return ticketService.listTicketsForUser(userId, pageable)
                            .map(ticketMapper::toListTicketResponseDto);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<GetTicketResponseDto> getTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        UUID userId = JwtUtil.parseUserId(jwt);

        return ticketService.getTicketForUser(ticketId, userId)
                .map(ticketMapper::toGetTicketResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ){
        byte[] qrCodeImageForUserAndTicket = qrCodeServicer.getQrCodeImageForUserAndTicket(JwtUtil.parseUserId(jwt), ticketId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImageForUserAndTicket.length);

        return ResponseEntity.ok().headers(headers).body(qrCodeImageForUserAndTicket);
    }
}
