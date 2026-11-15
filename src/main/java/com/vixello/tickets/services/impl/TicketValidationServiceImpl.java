package com.vixello.tickets.services.impl;

import com.vixello.tickets.domain.entities.*;
import com.vixello.tickets.exceptions.QrCodeNotFoundException;
import com.vixello.tickets.repositories.QrCodeRepository;
import com.vixello.tickets.repositories.TicketValidationRepository;
import com.vixello.tickets.services.TicketValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {
    private final TicketValidationRepository ticketValidationRepository;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(String.format(
                        "QR Core with ID %s was not found", qrCodeId
                )));// improve security later

        Ticket ticket = qrCode.getTicket();

        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(TicketValidationMethod.QR_SCAN);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(val -> TicketValidationStatusEnum.VALID.equals(val.getStatus()))
                .findFirst()
                .map(val -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        return null;
    }
}
