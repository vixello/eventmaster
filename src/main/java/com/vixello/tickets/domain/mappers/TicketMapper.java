package com.vixello.tickets.domain.mappers;

import com.vixello.tickets.domain.dtos.ListTicketResponseDto;
import com.vixello.tickets.domain.dtos.ListTicketTicketTypeResponseDto;
import com.vixello.tickets.domain.dtos.PurchaseTicketResponseDto;
import com.vixello.tickets.domain.dtos.PurchaseTicketTypeResponseDto;
import com.vixello.tickets.domain.entities.Ticket;
import com.vixello.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {

    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);
    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);

    PurchaseTicketResponseDto toPurchaseTicketResponseDto(Ticket ticket);
    PurchaseTicketTypeResponseDto toPurchaseTicketTypeResponseDto(TicketType ticketType);
}
