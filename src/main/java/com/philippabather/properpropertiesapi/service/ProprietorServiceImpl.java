package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.dto.RentalDTOOut;
import com.philippabather.properpropertiesapi.dto.SaleDTOOut;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(ProprietorServiceImpl.class);
    private ProprietorRepository proprietorRepo;
    private ModelMapper modelMapper;

    public ProprietorServiceImpl(ProprietorRepository proprietorRepo, ModelMapper modelMapper) {
        this.proprietorRepo = proprietorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ProprietorDTOOut> findAll() {
        logger.info("start: ProprietorServiceImpl_findAll");
        Set<Proprietor> proprietors = proprietorRepo.findAll();
        logger.info("end: ProprietorServiceImpl_findAll");
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllBySurname(String surname) {
        logger.info("start: ProprietorServiceImpl_findAllBySurname");
        Set<Proprietor> proprietors = proprietorRepo.findAllBySurname(surname);
        logger.info("end: ProprietorServiceImpl_findAllBySurname");
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByTelephone(String telephone) {
        logger.info("start: ProprietorServiceImpl_findAllByTelephone");
        Set<Proprietor> proprietors = proprietorRepo.findAllByTelephone(telephone);
        logger.info("end: ProprietorServiceImpl_findAllByTelephone");
        return convertToProprietorDTOOutSet(proprietors);
    }

    @Override
    public Set<ProprietorDTOOut> findAllByNumProperties(int numProperties) {
        logger.info("start: ProprietorServiceImpl_findAllByNumProperties");
        Set<Proprietor> proprietors = proprietorRepo.findAllByNumProperties(numProperties);
        logger.info("end: ProprietorServiceImpl_findAllByNumProperties");
        return convertToProprietorDTOOutSet(proprietors);
    }
    @Override
    public ProprietorDTOOut save(ProprietorDTOIn proprietorDTOIn) {
        logger.info("start: ProprietorServiceImpl_save");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Proprietor proprietor = new Proprietor();
        modelMapper.map(proprietorDTOIn, proprietor);

        proprietor.setNumProperties(0);
        proprietor.setPassword(bCryptPasswordEncoder.encode(proprietorDTOIn.getPassword()));

        Proprietor savedProprietor = proprietorRepo.save(proprietor);
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();

        modelMapper.map(savedProprietor, proprietorDTOOut);
        logger.info("end: ProprietorServiceImpl_save");
        return proprietorDTOOut;
    }

    @Override
    public Proprietor findByUsername(String username) {
        logger.info("ProprietorServiceImpl_findByUsername");
        return proprietorRepo.findByUsername(username);
    }
    @Override
    public ProprietorDTOOut findById(long proprietorId) throws ProprietorNotFoundException {
        logger.info("start: ProprietorServiceImpl_findById");
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        logger.info("end: ProprietorServiceImpl_findById");
        return getProprietorDTO(proprietor);
    }

    @Override
    public ProprietorDTOOut getProprietorDTO(Proprietor proprietor) {
        logger.info("start: ProprietorServiceImpl_getProprietorDTO");
        List<RentalProperty> rentals = proprietor.getRentalPropertyList();
        List<SaleProperty> sales = proprietor.getSalePropertyList();

        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(proprietor, proprietorDTOOut);

        // convert list of sale and rental properties to their DTOs
        List<RentalDTOOut> rentalsDTO = convertToRentalDTOOutList(rentals);
        List<SaleDTOOut> salesDTO = convertToSaleDTOOutList(sales);
        proprietorDTOOut.setRentalPropertyList(rentalsDTO);
        proprietorDTOOut.setSalePropertyList(salesDTO);

        logger.info("end: ProprietorServiceImpl_getProprietorDTO");
        return proprietorDTOOut;
    }


    @Override
    public ProprietorDTOOut updateById(long proprietorId, ProprietorDTOIn proprietorDTOIn) throws ProprietorNotFoundException {
        logger.info("start: ProprietorServiceImpl_updateById");
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        modelMapper.map(proprietorDTOIn, proprietor);
        Proprietor updatedProprietor = proprietorRepo.save(proprietor);
        ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
        modelMapper.map(updatedProprietor, proprietorDTOOut);
        logger.info("end: ProprietorServiceImpl_updateById");
        return proprietorDTOOut;
    }

    @Override
    public void updatePropertyDetails(Proprietor proprietor) {
        logger.info("ProprietorServiceImpl_updatePropertyDetails");
        proprietorRepo.save(proprietor);
    }

    @Override
    public void deleteById(long proprietorId) throws ProprietorNotFoundException {
        logger.info("start: ProprietorServiceImpl_deleteById");
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        proprietorRepo.delete(proprietor);
        logger.info("end: ProprietorServiceImpl_deleteById");
    }

    private Set<ProprietorDTOOut> convertToProprietorDTOOutSet(Set<Proprietor> proprietors) {
        logger.info("start: ProprietorServiceImpl_convertToProprietorDTOOutSet");
        Set<ProprietorDTOOut> proprietorsDTOOut = new HashSet<>();

        for (Proprietor proprietor :
                proprietors) {
            ProprietorDTOOut proprietorDTOOut = new ProprietorDTOOut();
            modelMapper.map(proprietor, proprietorDTOOut);
            proprietorsDTOOut.add(proprietorDTOOut);
        }

        logger.info("end: ProprietorServiceImpl_convertToProprietorDTOOutSet");
        return proprietorsDTOOut;
    }

    private List<RentalDTOOut> convertToRentalDTOOutList(List<RentalProperty> rentalProperties) {
        logger.info("start: ProprietorServiceImpl_convertToRentalDTOOutList");
        List<RentalDTOOut> rentalsDTOOut = new ArrayList<>();

        for (RentalProperty rental :
                rentalProperties) {
            RentalDTOOut rentalDTOOut = new RentalDTOOut();
            modelMapper.map(rental, rentalDTOOut);
            rentalsDTOOut.add(rentalDTOOut);
        }

        logger.info("end: ProprietorServiceImpl_convertToRentalDTOOutList");
        return rentalsDTOOut;
    }

    private List<SaleDTOOut> convertToSaleDTOOutList(List<SaleProperty> saleProperties) {
        logger.info("start: ProprietorServiceImpl_convertToSaleDTOOutList");
        List<SaleDTOOut> salesDTOOut = new ArrayList<>();

        for (SaleProperty sale :
                saleProperties) {
            SaleDTOOut saleDTOOut = new SaleDTOOut();
            modelMapper.map(sale, saleDTOOut);
            salesDTOOut.add(saleDTOOut);
        }

        logger.info("end: ProprietorServiceImpl_convertToSaleDTOOutList");
        return salesDTOOut;
    }
}
