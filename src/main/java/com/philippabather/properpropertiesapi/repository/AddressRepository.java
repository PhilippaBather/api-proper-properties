package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.Address;
import com.philippabather.properpropertiesapi.model.Region;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * AddressRepository - el repositorio para manejar la entidad Address ('dirección').
 *
 * @author Philippa Bather
 */

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    Set<Address> getAll();
    Set<Address> getAllByPostCode(String postcode);
    Set<Address> getAllByRegion(Region region);
    Set<Address> getAllByTown(String town);
    Optional<Address> findById(long addressId);
}
