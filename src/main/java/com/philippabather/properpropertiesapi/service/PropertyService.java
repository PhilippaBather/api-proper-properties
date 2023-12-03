package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.model.Property;

import java.util.List;
import java.util.PropertyPermission;
import java.util.Set;

/**
 * PropertyService - la interfaz para manejar la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
public interface PropertyService {

    // TODO - change list to SET
    List<Property> getAll();
    Property save (Property property);

    Property getById(long propertyId) throws PropertyNotFoundException;
    void deleteById(long propertyId) throws PropertyNotFoundException;
}
