package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.RentalProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

/**
 * RentalPropertyRepository - el repositorio para manejar la entidad RentalProperty ('alquiler').
 *
 * @author Philippa Bather
 */
@Repository
public interface RentalPropertyRepository extends CrudRepository<RentalProperty, Long> {

    Set<RentalProperty> findAll();
    Set<RentalProperty> findAllByRentPerMonth(BigDecimal monthlyRent);
    Set<RentalProperty> findAllByMinTenancy(int minTenancy);
    Set<RentalProperty> findAllByNumBedrooms(int numBedrooms);
    Optional<RentalProperty> findById(long propertyId);


}
