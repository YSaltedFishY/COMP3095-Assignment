package ca.gbc.eventservice.controller;

import ca.gbc.eventservice.dto.EventRequest;
import ca.gbc.eventservice.dto.EventResponse;
import ca.gbc.eventservice.service.EventService;
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
    public ResponseEntity<EventResponse> createEvent(@RequestBody EventRequest eventRequest){
        EventResponse createEvent = eventService.createEvent(eventRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/event/" + createEvent);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createEvent);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents(){
        return eventService.getAllEvents();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable("id") String id,
                                         @RequestBody EventRequest eventRequest){
        String updateEventId = eventService.updateEvent(id, eventRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/event/"+ updateEventId);

        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable("id") String id){
        eventService.deleteEvent(id);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
