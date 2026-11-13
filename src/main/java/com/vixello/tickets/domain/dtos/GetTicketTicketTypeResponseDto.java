package com.vixello.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTicketTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private GetTicketTypeEventDetailsResponseDto event;
}
