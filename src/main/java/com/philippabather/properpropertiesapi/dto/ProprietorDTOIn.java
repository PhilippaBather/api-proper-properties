package com.philippabather.properpropertiesapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_TELEPHONE_NOT_BLANK;


/**
 * ProprietorDTOIn - Data Transfer Object (DTO) para la clase Proprietor ('propietario') que extiende la clase abstracta de User.
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProprietorDTOIn extends UserDTOIn {

    @JsonProperty
    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    private boolean isAgency;

    @NotBlank(message = VALIDATION_TELEPHONE_NOT_BLANK)
    private String telephone;

}
