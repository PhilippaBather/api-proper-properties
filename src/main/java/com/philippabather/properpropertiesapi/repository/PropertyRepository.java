package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.Property;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.PropertyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * PropertyRepository - el repositorio para manejar la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    Set<Property> findAll();
    Set<Property> findAllByPropertyStatus(PropertyStatus propertyStatus);
    Set<Property> findAllByPropertyType(PropertyType propertyType);
    Set<Property> findAllByNumBedrooms(int numBedrooms);
    Property save(Property property);

    Optional<Property> findById(long propertyId);
}
