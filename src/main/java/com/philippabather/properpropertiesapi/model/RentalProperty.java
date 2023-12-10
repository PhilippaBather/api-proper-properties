package com.philippabather.properpropertiesapi.model;

import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * RentalProperty - un alquiler
 *
 * La clase extiende la clase Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rental_properties")
public class RentalProperty extends Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "A figure for monthly rent is required.")
    @Column(name ="monthly_rent")
    private BigDecimal rentPerMonth;

    @NotNull(message = "A figure for the deposit is required.")
    @Column
    private BigDecimal deposit;

    @Min(3)
    @Column(name = "min_tenancy")
    private int minTenancy;

    @NotNull(message = ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_furnished")
    private boolean isFurnished;

    @NotNull(message = ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_pet_friendly")
    private boolean isPetFriendly;

    @ManyToOne
    @JoinColumn(name = "proprietor_id_rental")
    private Proprietor proprietorRental;

    @OneToOne(cascade = CascadeType.ALL) // elimina la dirección si el inmueble está eliminado
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
