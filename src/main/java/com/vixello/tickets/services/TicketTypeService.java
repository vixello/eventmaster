package com.vixello.tickets.services;

import com.vixello.tickets.domain.dtos.PurchaseTicketTypeCartRequestDto;
import com.vixello.tickets.domain.entities.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketTypeService {
    List<Ticket> purchaseTicketsCart(UUID userId, PurchaseTicketTypeCartRequestDto requestDto);
}
