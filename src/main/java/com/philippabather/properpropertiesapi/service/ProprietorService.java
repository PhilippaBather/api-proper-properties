package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;

import java.util.Set;

/**
 * ProprietorService - la interfaz para manejar la entidad Proprietor ('propietario').
 *
 * @author Philippa Bather
 */

public interface ProprietorService {

    Set<ProprietorDTOOut> findAll();
    Set<ProprietorDTOOut> findAllBySurname(String surname);
    Set<ProprietorDTOOut> findAllByIsAgency(boolean isAgency);
    Set<ProprietorDTOOut> findAllByNumProperties(int numProperties);
    ProprietorDTOOut save(ProprietorDTOIn proprietorDTOIn);
    ProprietorDTOOut findById(long proprietorId) throws ProprietorNotFoundException;
    ProprietorDTOOut updateById(long proprietorId, ProprietorDTOIn proprietorDTOIn) throws ProprietorNotFoundException;
    void update(Proprietor proprietor);
    void deleteById(long proprietorId) throws ProprietorNotFoundException;
}
