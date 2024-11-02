package ca.gbc.userservice.model;

import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
import lombok.*;
import ca.gbc.userservice.model.UserType;

import java.math.BigDecimal;

@Entity
@Table(name="t_users") //class names single, table plural
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    //underlying orm will rely on postgres to take care of it
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autoincrement
    private Long id;

    //@NotBlank
    private String name;
    //@Email
    private String email;

    @Enumerated(EnumType.STRING)
    private UserType user_type;
    private String role;

};

