package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * ClientRepository - el repositorio para manejar la entidad Client ('cliente').
 *
 * @author Philippa Bather
 */

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Set<Client> findAll();
    Set<Client> findAllBySurname(String surname);
    Set<Client> findAllByName(String name);
    Set<Client> findAllByDob(LocalDate dob);
    Client save(Client client);

    Optional<Client> findById(long clientId);

}
