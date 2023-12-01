package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.model.Property;
import com.philippabather.properpropertiesapi.service.PropertyService;
import jakarta.el.PropertyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * PropertyController - controller para manejar los datos sobre entidaes de un inmueble ('Property').
 *
 * @author Philippa Bather
 */
@Validated
@RestController
public class PropertyController {

    // TODO - validation for incoming objects
    // TODO - DTOs

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    public ResponseEntity<List<Property>> getAllProperties() {

        // TODO - add query params for Rental and For_Sale

        List<Property> properties = propertyService.getAll();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("/properties")
    public ResponseEntity<Property> saveProperty(@Valid @RequestBody Property property) {
        Property newProperty = propertyService.save(property);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        Property property = propertyService.getById(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    // TODO - update mapping

    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        propertyService.deleteById(propertyId);
        return ResponseEntity.noContent().build(); // 204
    }
}
