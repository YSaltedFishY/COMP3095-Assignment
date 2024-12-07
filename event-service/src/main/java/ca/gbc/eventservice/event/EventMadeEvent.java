package ca.gbc.eventservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMadeEvent {
    private String eventId;
    private Long organizerId;
    private String organizerRole;
}
