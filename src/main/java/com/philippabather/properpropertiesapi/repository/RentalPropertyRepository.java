package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.RentalProperty;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "SELECT COUNT(*) FROM rental_properties r", nativeQuery = true)
    Integer getRentalCountNativeSQL();

    @Query(value = "SELECT * FROM rental_properties r WHERE r.num_bedrooms >= :bedrooms", nativeQuery = true)
    Set<RentalProperty> getRentalsByBedroomsNativeSQL(int bedrooms);

    @Query(value = "SELECT * FROM rental_properties r WHERE r.is_lift = TRUE AND r.is_parking = TRUE", nativeQuery = true)
    Set<RentalProperty> getRentalsByParkingAndLiftNativeSQL();

}
