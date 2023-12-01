package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PropertyRepository - el repositorio para manejar la entidad Property ('inmueble').
 *
 * @author Philippa Bather
 */
@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {

    List<Property> findAll();
    Property save(Property property);

    Optional<Property> findById(long propertyId);
}
