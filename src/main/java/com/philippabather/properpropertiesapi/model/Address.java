package com.philippabather.properpropertiesapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.*;

/**
 * Address - una dirección
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = VALIDATION_ADDRESS_NAME_OR_NUMBER)
    @Column
    private String nameOrNum;

    @Column
    private String flatNumber;

    @NotBlank(message = VALIDATION_ADDRESS_STREET)
    @Column
    private String street;

    @NotBlank(message = VALIDATION_ADDRESS_CITY)
    @Column
    private String town; // pueblo o ciudad

    @Column
    private Region region;

    @NotBlank(message = VALIDATION_ADDRESS_POSTCODE)
    @Column
    private String postCode;

}
