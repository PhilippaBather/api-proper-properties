package com.philippabather.properpropertiesapi.dto;

import lombok.Data;

/**
 * LoginDTOIn - El objeto de la transferencia de datos (DTO) para las detalles de login
 *
 * @author Philippa Bather
 */
@Data
public class LoginDTOIn {
    private String username;
    private String password;
}
