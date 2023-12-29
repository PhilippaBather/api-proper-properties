package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ClientDTOIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;

import java.time.LocalDate;
import java.util.Set;

/**
 * ClientService - la interfaz de servicio para manejar la entidad Client ('cliente').
 *
 * @author Philippa Bather
 */

public interface ClientService {

    Set<ClientDTOOut> findAll();
    Set<ClientDTOOut> findAllBySurname(String surname);
    Set<ClientDTOOut> findAllByName(String name);
    Set<ClientDTOOut> findAllByDOB(LocalDate dob);
    ClientDTOOut save(ClientDTOIn clientDTOIn);
    ClientDTOOut findById(long clientId) throws ClientNotFoundException;
    ClientDTOOut updateById(long clientId, ClientDTOIn clientDTOIn) throws ClientNotFoundException;
    void deleteById(long clientId) throws ClientNotFoundException;
}
