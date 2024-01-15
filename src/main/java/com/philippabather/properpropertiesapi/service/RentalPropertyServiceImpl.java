package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.RentalDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import com.philippabather.properpropertiesapi.repository.RentalPropertyRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * RentalPropertyServiceImpl - implementa la interfaz de servicio RentalProperty; maneja información sobre la entidad
 * RentalProperty ('alquiler').
 *
 * @author Philippa Bather
 */
@Service
public class RentalPropertyServiceImpl implements RentalPropertyService {

    private final Logger logger = LoggerFactory.getLogger(RentalPropertyServiceImpl.class);
    private final ProprietorService proprietorService;
    private final ProprietorRepository proprietorRepo;
    private final RentalPropertyRepository rentalRepo;
    private final ModelMapper modelMapper;

    public RentalPropertyServiceImpl(ProprietorService proprietorService, ProprietorRepository proprietorRepo,
                                     RentalPropertyRepository rentalRepo, ModelMapper modelMapper) {
        this.proprietorService = proprietorService;
        this.proprietorRepo = proprietorRepo;
        this.rentalRepo = rentalRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<RentalDTOOut> findAll() {
        logger.info("start: RentalPropertyServiceImpl_findAll");
        Set<RentalProperty> rentals = rentalRepo.findAll();
        logger.info("end: RentalPropertyServiceImpl_findAll");
        return convertToRentalDTOOutSet(rentals);
    }

    @Override
    public Set<RentalDTOOut> findAllByMonthlyRent(BigDecimal rentPerMonth) {
        logger.info("start: RentalPropertyServiceImpl_findAllByMonthlyRent");
        Set<RentalProperty> rentals = rentalRepo.findAllByRentPerMonth(rentPerMonth);
        logger.info("end: RentalPropertyServiceImpl_findAllByMonthlyRent");
        return convertToRentalDTOOutSet(rentals);
    }

    @Override
    public Set<RentalDTOOut> findAllByMinTenancy(int minTenancy) {
        logger.info("start: RentalPropertyServiceImpl_findAllByMinTenancy");
        Set<RentalProperty> rentals = rentalRepo.findAllByMinTenancy(minTenancy);
        logger.info("end: RentalPropertyServiceImpl_findAllByMinTenancy");
        return convertToRentalDTOOutSet(rentals);
    }

    @Override
    public Set<RentalDTOOut> findByNumBedrooms(int numBedrooms) {
        logger.info("start: RentalPropertyServiceImpl_findByNumBedrooms");
        Set<RentalProperty> rentals = rentalRepo.findAllByNumBedrooms(numBedrooms);
        logger.info("end: RentalPropertyServiceImpl_findByNumBedrooms");
        return convertToRentalDTOOutSet(rentals);
    }

    @Override
    public RentalDTOOut save(long proprietorId, RentalProperty rentalProperty) throws ProprietorNotFoundException {
        logger.info("start: RentalPropertyServiceImpl_save");
        // coge el objeto de Proprietor o lanza una excepción
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));

        // establece los campos de Proprietor y PropertyStatus
        rentalProperty.setProprietorRental(proprietor);
        rentalProperty.setPropertyStatus(PropertyStatus.RENTAL);  // establece por defecto

        // guarda el inmueble
        RentalProperty savedProperty = rentalRepo.save(rentalProperty);

        // actualiza las detalles del inmueble en el objeto de Proprietor y guarda dicho objeto
        proprietor.addRentalProperty(savedProperty);
        proprietorService.updatePropertyDetails(proprietor);

        logger.info("end: RentalPropertyServiceImpl_save");
        return mapToRentalDTOOut(savedProperty);
    }

    @Override
    public RentalDTOOut findById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyServiceImpl_findById");
        RentalProperty rental = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        logger.info("end: RentalPropertyServiceImpl_findById");
        return mapToRentalDTOOut(rental);
    }

    @Override
    public RentalDTOOut updateById(long propertyId, RentalProperty rentalPropertyDTOIn) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyServiceImpl_updateById");
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // coge el objeto Proprietor
        Proprietor proprietor = property.getProprietorRental();

        // mapea el objeto actualizado al original
        modelMapper.map(rentalPropertyDTOIn, property);
        // establace campos
        property.setId(propertyId);
        property.setProprietorRental(proprietor);
        property.setPropertyStatus(PropertyStatus.RENTAL);  // establece por defecto
        // guardalo
        RentalProperty updatedProperty = rentalRepo.save(property);

        // actualiza el objeto Proprietor
        proprietor.removeRentalProperty(property);
        proprietor.addRentalProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        logger.info("end: RentalPropertyServiceImpl_updateById");
        return mapToRentalDTOOut(updatedProperty);
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyServiceImpl_deleteById");
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // actualiza el objeto Proprietor
        Proprietor proprietor = property.getProprietorRental();
        proprietor.removeRentalProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        rentalRepo.delete(property);
        logger.info("end: RentalPropertyServiceImpl_deleteById");
    }

    @Override
    public void deleteAddressById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: RentalPropertyServiceImpl_deleteAddressById");
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        property.setAddress(null);
        rentalRepo.save(property);
        logger.info("end: RentalPropertyServiceImpl_deleteAddressById");
    }

    private Set<RentalDTOOut> convertToRentalDTOOutSet(Set<RentalProperty> rentalProperties) {
        logger.info("start: RentalPropertyServiceImpl_convertToRentalDTOOutSet");
        Set<RentalDTOOut> rentalsDTOOut = new HashSet<>();

        for (RentalProperty rental :
                rentalProperties) {
            RentalDTOOut rentalDTOOut = new RentalDTOOut();
            modelMapper.map(rental, rentalDTOOut);
            rentalsDTOOut.add(rentalDTOOut);
        }

        logger.info("end: RentalPropertyServiceImpl_convertToRentalDTOOutSet");
        return rentalsDTOOut;
    }

    private RentalDTOOut mapToRentalDTOOut(RentalProperty rentalProperty) {
        logger.info("start: RentalPropertyServiceImpl_mapToRentalDTOOut");
        RentalDTOOut rentalDTOOut = new RentalDTOOut();
        modelMapper.map(rentalProperty, rentalDTOOut);
        logger.info("end: RentalPropertyServiceImpl_mapToRentalDTOOut");
        return rentalDTOOut;
    }
}
