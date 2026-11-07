package com.vixello.tickets.domain.dtos;

import java.util.UUID;

public class PurchaseTicketResponseDto {
    private UUID id;
    private String name;
    private PurchaseTicketTypeResponseDto ticketType;
}
