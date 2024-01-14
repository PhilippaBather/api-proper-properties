package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.dto.RentalDTOOut;
import com.philippabather.properpropertiesapi.dto.SaleDTOOut;
import com.philippabather.properpropertiesapi.exception.InvalidLoginException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProprietorServiceImpl - implementa la interfaz de servicio ProprietorService; maneja información sobre la entidad
 * Proprietor ('propietario').
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
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllBySurname(String surname) {
        Set<Proprietor> proprietors = proprietorRepo.findAllBySurname(surname);
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByTelephone(String telephone) {
        Set<Proprietor> proprietors = proprietorRepo.findAllByTelephone(telephone);
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByNumProperties(int numProperties) {
        Set<Proprietor> proprietors = proprietorRepo.findAllByNumProperties(numProperties);
        return convertToProprietorDTOOutSet(proprietors);
    }
    @Override
    public ProprietorDTOOut save(ProprietorDTOIn proprietorDTOIn) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Proprietor proprietor = new Proprietor();
        modelMapper.map(proprietorDTOIn, proprietor);

        proprietor.setNumProperties(0);
        proprietor.setPassword(bCryptPasswordEncoder.encode(proprietorDTOIn.getPassword()));

        Proprietor savedProprietor = proprietorRepo.save(proprietor);
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();

        modelMapper.map(savedProprietor, proprietorDTOOut);
        return proprietorDTOOut;
    }

    @Override
    public Proprietor findByUsername(String username) {
        return proprietorRepo.findByUsername(username);
    }
    @Override
    public ProprietorDTOOut findById(long proprietorId) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        return getProprietorDTO(proprietor);
    }

    @Override
    public ProprietorDTOOut findByUsernameAndPassword(String username, String password) throws InvalidLoginException {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Proprietor proprietor = proprietorRepo.findByUsernameAndPassword(username, encodedPassword).orElseThrow(() -> new InvalidLoginException(username));
        return getProprietorDTO(proprietor);
    }

    @Override
    public ProprietorDTOOut getProprietorDTO(Proprietor proprietor) {
        List<RentalProperty> rentals = proprietor.getRentalPropertyList();
        List<SaleProperty> sales = proprietor.getSalePropertyList();

        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(proprietor, proprietorDTOOut);

        // convert list of sale and rental properties to their DTOs
        List<RentalDTOOut> rentalsDTO = convertToRentalDTOOutList(rentals);
        List<SaleDTOOut> salesDTO = convertToSaleDTOOutList(sales);
        proprietorDTOOut.setRentalPropertyList(rentalsDTO);
        proprietorDTOOut.setSalePropertyList(salesDTO);

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
    public void updatePropertyDetails(Proprietor proprietor) {
        proprietorRepo.save(proprietor);
    }

    @Override
    public void deleteById(long proprietorId) throws ProprietorNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        proprietorRepo.delete(proprietor);
    }

    private Set<ProprietorDTOOut> convertToProprietorDTOOutSet(Set<Proprietor> proprietors) {
        Set<ProprietorDTOOut> proprietorsDTOOut = new HashSet<>();

        for (Proprietor proprietor :
                proprietors) {
            ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
            modelMapper.map(proprietor, proprietorDTOOut);
            proprietorsDTOOut.add(proprietorDTOOut);
        }

        return proprietorsDTOOut;
    }

    private List<RentalDTOOut> convertToRentalDTOOutList(List<RentalProperty> rentalProperties) {
        List<RentalDTOOut> rentalsDTOOut = new ArrayList<>();

        for (RentalProperty rental :
                rentalProperties) {
            RentalDTOOut rentalDTOOut = new RentalDTOOut();
            modelMapper.map(rental, rentalDTOOut);
            rentalsDTOOut.add(rentalDTOOut);
        }

        return rentalsDTOOut;
    }

    private List<SaleDTOOut> convertToSaleDTOOutList(List<SaleProperty> saleProperties) {
        List<SaleDTOOut> salesDTOOut = new ArrayList<>();

        for (SaleProperty sale :
                saleProperties) {
            SaleDTOOut saleDTOOut = new SaleDTOOut();
            modelMapper.map(sale, saleDTOOut);
            salesDTOOut.add(saleDTOOut);
        }

        return salesDTOOut;
    }
}
