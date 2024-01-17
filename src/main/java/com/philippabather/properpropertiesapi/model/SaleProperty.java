package com.philippabather.properpropertiesapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_BOOLEAN_REQUIRED;
import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_SALE_PRICE;

/**
 * SaleProperty - un inmueble para vender
 *
 * La clase extiende la clase Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sale_properties")
public class SaleProperty extends Property {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @NotNull(message = VALIDATION_SALE_PRICE)
    @Column
    private BigDecimal price;

    @JsonProperty
    @NotNull(message = VALIDATION_BOOLEAN_REQUIRED)
    @Column(name = "is_leasehold")
    private boolean isLeasehold;

    @DateTimeFormat
    @Column(name = "construction_date")
    private LocalDate constructionDate;

    @ManyToOne
    @JoinColumn(name = "proprietor_id_sale")
    private Proprietor proprietorSale;

    @OneToOne(cascade = CascadeType.ALL) // elimina la dirección si el inmueble está eliminado
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

}
