package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * ProprietorServiceImpl - implementa la interfaz ProprietorService; maneja información sobre la entidad Proprietor ('propietario').
 *
 * @author Philippa Bather
 */
@Service
public class ProprietorServiceImpl implements ProprietorService {

    private ProprietorRepository proprietorRepo;
    private ModelMapper modelMapper;

    public ProprietorServiceImpl(ProprietorRepository proprietorRepo, ModelMapper modelMapper) {
        this.proprietorRepo = proprietorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ProprietorDTOOut> findAll() {
        Set<Proprietor> proprietors = proprietorRepo.findAll();
        return convertToClientDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllBySurname(String surname) {
        Set<Proprietor> proprietors = proprietorRepo.findAllBySurname(surname);
        return convertToClientDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByIsAgency(boolean isAgency) {
        Set<Proprietor> proprietors = proprietorRepo.findAllByIsAgency(isAgency);
        return convertToClientDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByNumProperties(int numProperties) {
        Set<Proprietor> proprietors = proprietorRepo.findAllByNumProperties(numProperties);
        return convertToClientDTOOutSet(proprietors);
    }

    @Override
    public ProprietorDTOOut save(ProprietorDTOIn proprietorDTOIn) {
        Proprietor proprietor = new Proprietor();
        modelMapper.map(proprietorDTOIn, proprietor);
        Proprietor savedProprietor = proprietorRepo.save(proprietor);
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(savedProprietor, proprietorDTOOut);
        return proprietorDTOOut;
    }

    @Override
    public ProprietorDTOOut findById(long proprietorId) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(proprietor, proprietorDTOOut);
        return proprietorDTOOut;
    }

    @Override
    public ProprietorDTOOut updateById(long proprietorId, ProprietorDTOIn proprietorDTOIn) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        modelMapper.map(proprietorDTOIn, proprietor);
        Proprietor updatedProprietor = proprietorRepo.save(proprietor);
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(updatedProprietor, proprietorDTOOut);
        return proprietorDTOOut;
    }

    @Override
    public void deleteById(long proprietorId) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        proprietorRepo.delete(proprietor);
    }

    private Set<ProprietorDTOOut> convertToClientDTOOutSet(Set<Proprietor> proprietors) {
        Set<ProprietorDTOOut> proprietorsDTOOut = new HashSet<>();

        for (Proprietor proprietor :
                proprietors) {
            ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
            modelMapper.map(proprietor, proprietorDTOOut);
            proprietorsDTOOut.add(proprietorDTOOut);
        }

        return proprietorsDTOOut;
    }
}
