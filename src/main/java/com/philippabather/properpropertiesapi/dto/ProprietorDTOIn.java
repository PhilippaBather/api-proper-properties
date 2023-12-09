package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * ClientDTO - Data Transfer Object (DTO) para la clase Proprietor ('propietario') que extiende la clase abstracta de User.
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProprietorDTOIn extends UserDTOIn {

    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    private boolean isAgency;


}
