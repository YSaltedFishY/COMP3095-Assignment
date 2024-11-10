package ca.gbc.eventservice.repository;

import ca.gbc.eventservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

    List<Event> findByOrganizerId(String organizerId);
    Event findByBookingId(String bookingId);

}
