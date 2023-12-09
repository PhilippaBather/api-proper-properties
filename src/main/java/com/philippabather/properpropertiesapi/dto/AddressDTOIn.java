package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.model.Country;
import com.philippabather.properpropertiesapi.model.Region;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.*;

/**
 * AddressDTOIn - Data Transfer Object (DTO) para la clase Address ('dirección').
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOIn {

    @NotBlank(message = VALIDATION_ADDRESS_NAME_OR_NUMBER)
    private String nameOrNum;

    private String flatNumber;

    @NotBlank(message = VALIDATION_ADDRESS_STREET)
    private String street;

    @NotBlank(message = VALIDATION_ADDRESS_CITY)
    private String town; // pueblo o ciudad

    private Region region;

    @NotBlank(message = VALIDATION_ADDRESS_POSTCODE)
    private String postCode;

    private Country country;

}
