package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.PropertyDTOIn;
import com.philippabather.properpropertiesapi.dto.PropertyDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyStatusNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyTypeNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.service.PropertyService;
import jakarta.el.PropertyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * PropertyController - controller para manejar los datos sobre entidaes de un inmueble ('Property').
 *
 * @author Philippa Bather
 */
@Validated
@RestController
public class PropertyController {
    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/properties")
    public ResponseEntity<Set<PropertyDTOOut>> getAllProperties(@RequestParam(value = "propertyType", defaultValue = "") String propertyType,
                                                                @RequestParam(value = "propertyStatus", defaultValue = "") String propertyStatus,
                                                                @RequestParam(value = "numBedrooms", defaultValue = "0") int numBedrooms)
            throws PropertyStatusNotFoundException, PropertyTypeNotFoundException {
        Set<PropertyDTOOut> properties = new HashSet<>();
        if (propertyType.trim().equals("") && propertyStatus.trim().equals("") && numBedrooms == 0) {
            properties = propertyService.findAll();
        } else if (propertyType.trim().length() >= 1) {
            properties = propertyService.findAllByPropertyType(propertyType);
        } else if (propertyStatus.trim().length() >= 1) {
            properties = propertyService.findAllByPropertyStatus(propertyStatus);
        } else if (numBedrooms > 0) {
            properties = propertyService.findAllByNumBedrooms(numBedrooms);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("/properties/{proprietorId}")
    public ResponseEntity<PropertyDTOOut> saveProperty(@PathVariable long proprietorId, @Valid @RequestBody PropertyDTOIn property)
            throws ProprietorNotFoundException {
        PropertyDTOOut newProperty = propertyService.save(proprietorId, property);
        return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
    }

    @GetMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyDTOOut> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        PropertyDTOOut property = propertyService.getById(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/properties/{propertyId}")
    public ResponseEntity<PropertyDTOOut> updatePropertyById(@PathVariable long propertyId, @Valid @RequestBody PropertyDTOIn propertyDTOIn)
            throws PropertyNotFoundException {
        PropertyDTOOut property = propertyService.updateById(propertyId, propertyDTOIn);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Void> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        propertyService.deleteById(propertyId);
        return ResponseEntity.noContent().build(); // 204
    }
}
