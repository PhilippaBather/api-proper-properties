package com.philippabather.properpropertiesapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_BOOLEAN_REQUIRED;
import static com.philippabather.properpropertiesapi.constants.ValidationMessages.VALIDATION_TELEPHONE_NOT_BLANK;

/**
 * Proprietor - el propietario de un inmueble.
 *
 * La clase extiende la clase User.
 *
 * @author Philippa Bather
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "proprietors")
public class Proprietor extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty
    @NotNull(message= VALIDATION_BOOLEAN_REQUIRED)
    @Column(name ="is_agency")
    private boolean isAgency;
    
    @Min(0)
    @Column
    private int numProperties;

    @NotBlank(message = VALIDATION_TELEPHONE_NOT_BLANK)
    @Column
    private String telephone;

    @OneToMany(targetEntity = RentalProperty.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "proprietor_rental_properties")
    private List<RentalProperty> rentalPropertyList = new ArrayList<>();

    @OneToMany(targetEntity = SaleProperty.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "proprietor_sale_properties")
    private List<SaleProperty> salePropertyList = new ArrayList<>();

    public void addRentalProperty(RentalProperty property) {
        rentalPropertyList.add(property);
        incrementNumProperties();
    }

    public void removeRentalProperty(RentalProperty property) {
        rentalPropertyList.remove(property);
        decrementNumProperties();
    }

    public void addSaleProperty(SaleProperty property) {
        salePropertyList.add(property);
        incrementNumProperties();
    }

    public void removeSaleProperty(SaleProperty property) {
        salePropertyList.remove(property);
        decrementNumProperties();
    }

    private void incrementNumProperties() {
        numProperties++;
    }
    private void decrementNumProperties() {
        numProperties--;
    }
}
