package com.philippabather.properpropertiesapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.*;
import static com.philippabather.properpropertiesapi.constants.ValidationRegex.VALIDATION_PASSWORD_REGEX;

/**
 * User - el usuario general del API.
 *
 * La entidad es una clase abstracta y un 'mapped superclass' que comparte las propiedades con las subclases Client y
 * Proprietor.
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User {

    @Column
    @NotBlank(message = VALIDATION_USERNAME_NOT_BLANK)
    @Size(min = 5, max = 55, message = "Username must be no longer than 55 characters")
    private String username;

    // contraseña debe incluir 1 número y un carácter especial con tamaño mínimo de 8 carácteres y máximo de 25
//    @Pattern(regexp = "^(?=.*[\\d])(?=.*[!@#$%^&*_])[\\w!@#$%^&*]{8,25}$",
//            message = VALIDATION_PASSWORD)
    @Pattern(regexp = VALIDATION_PASSWORD_REGEX,
            message = VALIDATION_PASSWORD)
    @Column
    private String password;

    @Column
    @NotBlank(message = VALIDATION_NAME_NOT_BLANK)
    private String name;

    @Column
    @NotBlank(message = VALIDATION_SURNAME_NOT_BLANK)
    private String surname;

    @Column
    @Email(message = VALIDATION_EMAIL)
    private String email;

    @Column(name = "account_created")
    @NotNull(message = VALIDATION_ACCOUNT_CREATION_NOT_NULL)
    @DateTimeFormat
    private LocalDateTime accountCreated; // fecha de la creacíón de la cuenta

}
