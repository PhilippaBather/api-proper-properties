package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ClientDTOIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.model.Client;

import java.util.Set;

/**
 * ClientService - la interfaz para manejar la entidad de un cliente ('client').
 *
 * @author Philippa Bather
 */

public interface ClientService {

    Set<ClientDTOOut> findAll();
    Client save(ClientDTOIn clientDTOIn);
    ClientDTOOut findById(long clientId) throws ClientNotFoundException;
}
