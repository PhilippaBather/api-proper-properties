package com.philippabather.properpropertiesapi.dto;

import com.philippabather.properpropertiesapi.model.Country;
import com.philippabather.properpropertiesapi.model.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AddressDTOOut - El objeto de la transferencia de datos (DTO) de la clase Address ('dirección') al cliente/consumo de API.
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTOOut {

    // TODO - relationship between Address and Property

    private long id;
    private String nameOrNum;
    private String flatNumber;
    private String street;
    private String city; // pueblo o ciudad
    private Region region;
    private String postCode;
    private Country country;
}
