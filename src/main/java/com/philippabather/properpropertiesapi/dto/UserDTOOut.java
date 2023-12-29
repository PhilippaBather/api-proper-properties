package com.philippabather.properpropertiesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * UserDTOOut - El objeto de la transferencia de datos (DTO) de la clase User ('usario') al cliente/consumo de API.
 *
 * @author Philippa Bather
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTOOut {

    private String username;
    private String name;
    private String surname;
    private String email;
}
