package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.PropertyDTOIn;
import com.philippabather.properpropertiesapi.dto.PropertyDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyStatusNotFoundException;
import com.philippabather.properpropertiesapi.exception.PropertyTypeNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Property;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.repository.PropertyRepository;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * PropertyServiceImpl - implementa la interfaz de servicio PropertyService; maneja información sobre la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepo;
    private final ProprietorRepository proprietorRepo;
    private final ProprietorService proprietorService;
    private final ModelMapper modelMapper;

    public PropertyServiceImpl(PropertyRepository propertyRepo, ProprietorRepository proprietorRepo, ProprietorService proprietorService, ModelMapper modelMapper) {
        this.propertyRepo = propertyRepo;
        this.proprietorRepo = proprietorRepo;
        this.proprietorService = proprietorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<PropertyDTOOut> findAll() {
        Set<Property> properties = propertyRepo.findAll();
        return convertToPropertyDTOOutSet(properties);
    }

    @Override
    public Set<PropertyDTOOut> findAllByPropertyStatus(String propertyStatus) throws PropertyStatusNotFoundException {
        PropertyStatus propertyStatusEnum = switch (propertyStatus.toUpperCase()) {
            case "RENTAL" -> PropertyStatus.RENTAL;
            case "SALE" -> PropertyStatus.SALE;
            default -> throw new PropertyStatusNotFoundException(propertyStatus.toUpperCase());
        };
        Set<Property> properties = propertyRepo.findAllByPropertyStatus(propertyStatusEnum);
        return convertToPropertyDTOOutSet(properties);
    }

    @Override
    public Set<PropertyDTOOut> findAllByPropertyType(String propertyType) throws PropertyTypeNotFoundException {
        PropertyType propertyTypeEnum = switch (propertyType.toUpperCase()) {
            case "COMMERCIAL" -> PropertyType.COMMERCIAL;
            case "FLAT" -> PropertyType.FLAT;
            case "HOUSE" -> PropertyType.HOUSE;
            default -> throw new PropertyTypeNotFoundException(propertyType.toUpperCase());
        };
        Set<Property> properties = propertyRepo.findAllByPropertyType(propertyTypeEnum);
        return convertToPropertyDTOOutSet(properties);
    }

    @Override
    public Set<PropertyDTOOut> findAllByNumBedrooms(int numBedrooms) {
        Set<Property> properties = propertyRepo.findAllByNumBedrooms(numBedrooms);
        return convertToPropertyDTOOutSet(properties);
    }

    @Override
    public PropertyDTOOut save(long proprietorId, PropertyDTOIn propertyDTOIn) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        Property property = new Property();
        modelMapper.map(propertyDTOIn, property);
        property.setProprietor(proprietor);

        Property savedProperty = propertyRepo.save(property);
        proprietor.addProperty(savedProperty);
        proprietorService.update(proprietor); // update Proprietor object's numProperties field

        PropertyDTOOut propertyDTOOut = new PropertyDTOOut();
        modelMapper.map(savedProperty, propertyDTOOut);

        return propertyDTOOut;
    }

    @Override
    public PropertyDTOOut getById(long propertyId) throws PropertyNotFoundException {
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        PropertyDTOOut propertyDTOOut = new PropertyDTOOut();
        modelMapper.map(property, propertyDTOOut);
        return propertyDTOOut;
    }

    @Override
    public PropertyDTOOut updateById(long propertyId, PropertyDTOIn propertyDTOIn) throws PropertyNotFoundException {
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new ProprietorNotFoundException(propertyId));

        modelMapper.map(propertyDTOIn, property);
        property.setId(propertyId);
        Property updatedProperty = propertyRepo.save(property);

        PropertyDTOOut propertyDTOOut = new PropertyDTOOut();
        modelMapper.map(updatedProperty, propertyDTOOut);

        return propertyDTOOut;
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        Property property = propertyRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        Proprietor proprietor = property.getProprietor();

        propertyRepo.delete(property);

        proprietor.removeProperty(property);
        proprietorService.update(proprietor);  // update Proprietor object's numProperties field

    }

    private Set<PropertyDTOOut> convertToPropertyDTOOutSet(Set<Property> properties) {
        Set<PropertyDTOOut> propertiesDTOOut = new HashSet<>();

        for (Property property :
                properties) {
            PropertyDTOOut propertyDTOOut = new PropertyDTOOut();
            modelMapper.map(property, propertyDTOOut);
            propertiesDTOOut.add(propertyDTOOut);
        }

        return propertiesDTOOut;
    }
}
