package com.vixello.tickets.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PurchaseTicketTypeCartRequestDto {
    @NotEmpty(message = "Cart cannot be empty")
    private List<@Valid CartItemDto> items;

    @Data
    public static class CartItemDto {
        @NotNull(message = "Ticket type ID is required")
        private UUID ticketTypeId;

        @Min(value = 1, message = "Quantity must be at least 1")
        @Max(value = 4, message = "Cannot purchase more than 4 tickets per request")
        private int quantity;
    }
}
