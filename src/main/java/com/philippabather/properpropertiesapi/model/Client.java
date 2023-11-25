package com.philippabather.properpropertiesapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Client - el usuario que busca un inmueble.
 *
 * La clase extiende la clase User.
 *
 * @author Philippa Bather
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "clients")
public class Client extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat
    @Column
    private LocalDateTime dob; // fecha de nacimiento (date of birth)

    @Column(name = "is_student")
    private boolean isStudent;
}
