package ca.gbc.bookingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

//TODO: Required constructor
@Document(collection = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    private String bookingId;
    private String userId;
    private String roomId;
    private Date startTime;
    private Date endTime;
    private String purpose;
    // Getters and Setters
}
