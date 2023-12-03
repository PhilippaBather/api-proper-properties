package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.Proprietor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * ProprietorRepository - el repositorio para manejar la entidad Proprietor ('propietario').
 *
 * @author Philippa Bather
 */

@Repository
public interface ProprietorRepository extends CrudRepository<Proprietor, Long> {

    Set<Proprietor> findAll();
    Set<Proprietor> findAllBySurname(String surname);
    Set<Proprietor> findAllByIsAgency(boolean isAgency);
    Set<Proprietor> findAllByNumProperties(int numProperties);

    Optional<Proprietor> findById(long proprietorId);
}
