package com.philippabather.properpropertiesapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * ClientDTOOut - El objeto de la transferencia de datos (DTO) de la clase Client al cliente/consumo de API; extiende UserDTOOut.
 *
 * @author Philippa Bather
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTOOut extends UserDTOOut{

    private long id;
    private LocalDate dob; // fecha de nacimiento (date of birth)
    private boolean isStudent;
}
