package com.philippabather.properpropertiesapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.philippabather.properpropertiesapi.constants.ValidationMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @NotNull(message= ValidationMessages.VALIDATION_BOOLEAN_REQUIRED)
    @Column(name ="is_agency")
    private boolean isAgency;
    
    @Min(0)
    @Column
    private int numProperties;

    @OneToMany(mappedBy = "proprietor")
    @JsonBackReference(value = "proprietor_properties")
    private List<Property> propertyList = new ArrayList<>();

    public void addProperty(Property property) {
        propertyList.add(property);
        incrementNumProperties();
    }

    public void removeProperty(Property property) {
        propertyList.remove(property);
        decrementNumProperties();
    }

    private void incrementNumProperties() {
        numProperties++;
    }
    private void decrementNumProperties() {
        numProperties--;
    }
}
