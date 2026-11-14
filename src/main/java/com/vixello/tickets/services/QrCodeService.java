package com.vixello.tickets.services;

import com.vixello.tickets.domain.entities.QrCode;
import com.vixello.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
