package com.vixello.tickets.domain.mappers;

import com.vixello.tickets.domain.dtos.TicketValidationResponseDto;
import com.vixello.tickets.domain.entities.TicketValidation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TicketValidationMapper {
    @Mapping(target = "ticketId", source = "ticket.id")
    abstract TicketValidationResponseDto toTicketValidationResponseDto(TicketValidation ticketValidation);
}
