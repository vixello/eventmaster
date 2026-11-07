package com.vixello.tickets.services.impl;

import com.vixello.tickets.domain.dtos.PurchaseTicketTypeCartRequestDto;
import com.vixello.tickets.domain.entities.Ticket;
import com.vixello.tickets.domain.entities.TicketStatusEnum;
import com.vixello.tickets.domain.entities.TicketType;
import com.vixello.tickets.domain.entities.User;
import com.vixello.tickets.exceptions.TicketSoldOutException;
import com.vixello.tickets.exceptions.TicketTypeNotFoundException;
import com.vixello.tickets.exceptions.UserNotFoundException;
import com.vixello.tickets.repositories.TicketRepository;
import com.vixello.tickets.repositories.TicketTypeRepository;
import com.vixello.tickets.repositories.UserRepository;
import com.vixello.tickets.services.QrCodeService;
import com.vixello.tickets.services.TicketTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public List<Ticket> purchaseTicketsCart(UUID userId, PurchaseTicketTypeCartRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User with ID %s was not found", userId)
        ));

        List<Ticket> ticketsToSave = new ArrayList<>();
        for(PurchaseTicketTypeCartRequestDto.CartItemDto ticketTypeItem : requestDto.getItems()){
            TicketType ticketType = ticketTypeRepository.findByIdWithLock(ticketTypeItem.getTicketTypeId()).orElseThrow(() -> new TicketTypeNotFoundException(
                    String.format("Ticket type with ID %s was not found", ticketTypeItem.getTicketTypeId())
            ));

            int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeItem.getTicketTypeId());
            Integer totalAvailable = ticketType.getTotalAvailable();

            if(purchasedTickets + ticketTypeItem.getQuantity() > totalAvailable){
                throw new TicketSoldOutException();
            }

            for(int i = 0; i <  ticketTypeItem.getQuantity(); i++){
                Ticket ticket = new Ticket();
                ticket.setStatus(TicketStatusEnum.PURCHASED);
                ticket.setTicketType(ticketType);
                ticket.setPurchaser(user);
                ticketsToSave.add(ticket);
            }

        }

        List<Ticket> savedTickets = ticketRepository.saveAll(ticketsToSave);

        for(Ticket ticket : savedTickets){
            qrCodeService.generateQrCode(ticket);
        }

        return ticketRepository.saveAll(savedTickets);
    }
}
