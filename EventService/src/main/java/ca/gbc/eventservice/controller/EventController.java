package ca.gbc.eventservice.controller;


import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;

import ca.gbc.eventservice.service.EventService;

import ca.gbc.eventservice.service.EventServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {


    private final EventService eventService;



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest) {
        EventResponse createdEvent = eventService.createEvent(eventRequest);



        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/event/" + createdEvent.organizerId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdEvent);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents() {
        return eventService.getAllEvents();

    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse getEventByBookingId(@PathVariable("bookingId") String bookingId){

         return eventService.getEventWithBookingId(bookingId);

    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventResponse> updateEvent(@PathVariable("eventId") String eventId, @RequestBody EventRequest eventRequest) {

        String id = eventService.updateEvent(eventId, eventRequest);
        HttpHeaders header = new HttpHeaders();
        header.add("Location", "/api/event/" + id);

        return new ResponseEntity<>(header,HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<EventResponse> deleteEvent(@PathVariable("eventId") String eventId){

        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
