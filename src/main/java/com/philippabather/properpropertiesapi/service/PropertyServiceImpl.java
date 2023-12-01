package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.model.Property;
import com.philippabather.properpropertiesapi.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * PropertyServiceImpl - implementa la interfaz PropertyService; maneja información sobre la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepo;

    public PropertyServiceImpl(PropertyRepository propertyRepo) {
        this.propertyRepo = propertyRepo;
    }

    @Override
    public List<Property> getAll() {
        return propertyRepo.findAll();
    }

    @Override
    public Property save(Property property) {
        return propertyRepo.save(property);
    }

    @Override
    public Property getById(long propertyId) throws PropertyNotFoundException {
        return propertyRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        propertyRepo.delete(property);
    }
}
