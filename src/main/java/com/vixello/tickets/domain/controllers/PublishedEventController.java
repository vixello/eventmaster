package com.vixello.tickets.domain.controllers;

import com.vixello.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.vixello.tickets.domain.dtos.ListPublishedEventResponseDto;
import com.vixello.tickets.domain.entities.Event;
import com.vixello.tickets.domain.mappers.EventMapper;
import com.vixello.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> getPublishedEvents(
            @RequestParam(required = false, name = "searchTerm") String searchTerm,
            @RequestParam(required = false, name = "dateFrom") String dateFrom,
            @RequestParam(required = false, name = "dateTo") String dateTo,
            Pageable pageable){

        System.out.println("DEBUG -> searchTerm: " + searchTerm + ", dateFrom: " + dateFrom + ", dateTo: " + dateTo);

        boolean hasSearch = searchTerm != null && !searchTerm.trim().isEmpty();
        boolean hasDateFrom = dateFrom != null && !dateFrom.trim().isEmpty();
        boolean hasDateTo = dateTo != null && !dateTo.trim().isEmpty();

        Page<Event> events;
        if (hasSearch || hasDateFrom || hasDateTo) {
            events = eventService.searchPublishedEvents(searchTerm, dateFrom, dateTo, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }

        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
    }

    @GetMapping(path="/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvent(
            @PathVariable UUID eventId)
    {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
