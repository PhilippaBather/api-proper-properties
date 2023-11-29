package com.philippabather.properpropertiesapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * ClientDTO - Data Transfer Object (DTO) para la clase 'Client' que extiende la clase abstracta de User.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTOInIn extends UserDTOIn {

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat
    private LocalDateTime dob; // fecha de nacimiento (date of birth)

    // TODO - validation for boolean
    private boolean isStudent;
}
