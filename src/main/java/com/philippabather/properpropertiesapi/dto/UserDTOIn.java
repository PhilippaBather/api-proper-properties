package com.philippabather.properpropertiesapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * UserDTO - El objeto de la transferencia de datos (DTO) de la clase User desde cliente/consumo de API.
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserDTOIn {

    @NotBlank(message = "Username must not be blank")
    @Max(value = 55, message = "Username must be no longer than 55 characters")
    private String username;

    // contraseña debe incluir 1 número y un carácter especial con tamaño mínimo de 8 carácteres y máximo de 25
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[!@#$%^&*])[\\w!@#$%^&*]{8,25}$",
            message = "Password must contain minimum 8 characters, including at least 1 digit and 1 special character; the maximum length is of 25 characters")
    private String password;

    @NotBlank(message = "A name is required.")
    private String name;

    @NotBlank(message = "Surname is required.")
    private String surname;

    @Email(message = "A valid email is required.")
    private String email;

    @NotNull(message = "Date of account creation is required.")
    @DateTimeFormat()
    private LocalDateTime accountCreated; // fecha de la creacíón de la cuenta
}
