package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.InvalidLoginException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;

import java.util.Set;

/**
 * ProprietorService - la interfaz de servicio para manejar la entidad Proprietor ('propietario').
 *
 * @author Philippa Bather
 */
public interface ProprietorService {

    Set<ProprietorDTOOut> findAll();
    Set<ProprietorDTOOut> findAllBySurname(String surname);
    Set<ProprietorDTOOut> findAllByTelephone(String telephone);
    Set<ProprietorDTOOut> findAllByNumProperties(int numProperties);
    ProprietorDTOOut save(ProprietorDTOIn proprietorDTOIn);
    ProprietorDTOOut findById(long proprietorId) throws ProprietorNotFoundException;
    ProprietorDTOOut findByUsernameAndPassword(String username, String password) throws InvalidLoginException;
    ProprietorDTOOut updateById(long proprietorId, ProprietorDTOIn proprietorDTOIn) throws ProprietorNotFoundException;
    void updatePropertyDetails(Proprietor proprietor);
    void deleteById(long proprietorId) throws ProprietorNotFoundException;
}
