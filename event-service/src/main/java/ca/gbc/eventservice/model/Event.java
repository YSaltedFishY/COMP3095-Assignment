package ca.gbc.eventservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(value = "event")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Event {
    @Id
    private String id;

    private String bookingId;
    private Long roomId;

    private Long organizerId; //UserId from user-service
    private String organizerName;
    private String organizerRole;
    private String eventName;
    private String eventType;
    private int expectedAttendees;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String email;
    private String approval;

}
