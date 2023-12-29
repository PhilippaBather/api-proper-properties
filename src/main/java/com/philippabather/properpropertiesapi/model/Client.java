package com.philippabather.properpropertiesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_DOB_NOT_NULL;

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

    @NotNull(message = VALIDATION_DOB_NOT_NULL)
    @DateTimeFormat
    @Column
    private LocalDate dob; // fecha de nacimiento (date of birth)

    @JsonProperty
    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_student")
    private Boolean isStudent;
}
