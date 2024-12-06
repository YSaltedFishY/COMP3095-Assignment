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
    private String eventName;
    private Long organizerId;//UserId from user-service
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String bookingId;
    private String eventType;
    private int expectedAttendees;

}
