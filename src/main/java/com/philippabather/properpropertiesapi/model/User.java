package com.philippabather.properpropertiesapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
    @NotBlank(message = "Username must not be blank")
    @Size(min = 5, max = 55, message = "Username must be no longer than 55 characters")
    private String username;

    // contraseña debe incluir 1 número y un carácter especial con tamaño mínimo de 8 carácteres y máximo de 25
    @Pattern(regexp = "^(?=.*[\\d])(?=.*[!@#$%^&*_])[\\w!@#$%^&*]{8,25}$",
            message = "Password must contain minimum 8 characters, including at least 1 digit and 1 special character; the maximum length is of 25 characters")
    @Column
    private String password;

    @Column
    @NotBlank(message = "A name is required.")
    private String name;

    @Column
    @NotBlank(message = "Surname is required.")
    private String surname;

    @Column
    @Email(message = "A valid email is required.")
    private String email;

    @Column(name = "account_created")
    @NotNull(message = "Date of account creation is required.")
    @DateTimeFormat
    private LocalDateTime accountCreated; // fecha de la creacíón de la cuenta

}
