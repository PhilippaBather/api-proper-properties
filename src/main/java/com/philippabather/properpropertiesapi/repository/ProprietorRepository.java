package com.philippabather.properpropertiesapi.repository;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
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
    Set<Proprietor> findAllByTelephone(String telephone);
    Set<Proprietor> findAllByNumProperties(int numProperties);

    Optional<Proprietor> findById(long proprietorId);
    Optional<Proprietor> findByUsernameAndPassword(String username, String password);
    Proprietor findByUsername(String username);
//    ProprietorDTOOut findByUsername(String username);
}
