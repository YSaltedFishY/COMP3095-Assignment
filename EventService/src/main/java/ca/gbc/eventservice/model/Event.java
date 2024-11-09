package ca.gbc.eventservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    private String id;
    private String eventName;
    private String organizerId;
    private String eventType;
    private int expectedAttendees;
    private String roomId;

}
