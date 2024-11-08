package ca.gbc.roomservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="t_rooms") //class names single, table plural
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Room {
    @Id
    //underlying orm will rely on postgres to take care of it
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;


    private Boolean availability;
    private String room_name;
    private String features;
    private BigDecimal price;
    private Integer capacity;

}
