package com.philippabather.properpropertiesapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.*;
import static com.philippabather.properpropertiesapi.constants.ValidationRegex.VALIDATION_PASSWORD_REGEX;

/**
 * UserDTO - El objeto de la transferencia de datos (DTO) de la clase User ('usuario') desde cliente/consumo de API.
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDTOIn {

    @NotBlank(message = VALIDATION_USERNAME_NOT_BLANK)
    @Size(min = 5, max = 55, message = "Username must be longer than 5 characters and no longer than 55 characters")
    private String username;

    // contraseña debe incluir 1 número y un carácter especial con tamaño mínimo de 8 carácteres y máximo de 25
    @Pattern(regexp = VALIDATION_PASSWORD_REGEX,
            message = VALIDATION_PASSWORD)
    private String password;

    @NotBlank(message = VALIDATION_NAME_NOT_BLANK)
    private String name;

    @NotBlank(message = VALIDATION_SURNAME_NOT_BLANK)
    private String surname;

    @Email(message = VALIDATION_EMAIL)
    private String email;

    @NotNull(message = VALIDATION_ACCOUNT_CREATION_NOT_NULL)
    @DateTimeFormat()
    private LocalDateTime accountCreated; // fecha de la creacíón de la cuenta
}
