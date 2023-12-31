package com.philippabather.properpropertiesapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_DOB_NOT_NULL;

/**
 * ClientDTO - Data Transfer Object (DTO) para la clase Client ('cliente') que extiende la clase abstracta de User.
 *
 * @author Philippa Bather
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDTOIn extends UserDTOIn {

    @NotNull(message = VALIDATION_DOB_NOT_NULL)
    @DateTimeFormat
    private LocalDate dob; // fecha de nacimiento (date of birth)

    @JsonProperty
    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    private Boolean isStudent;
}
