package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.RentalProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import com.philippabather.properpropertiesapi.repository.RentalPropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

/**
 * RentalPropertyServiceImpl - implementa la interfaz de servicio RentalProperty; maneja información sobre la entidad
 * RentalProperty ('alquiler').
 *
 * @author Philippa Bather
 */
@Service
public class RentalPropertyServiceImpl implements RentalPropertyService {

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
    public Set<RentalProperty> findAll() {
        return rentalRepo.findAll();
    }

    @Override
    public Set<RentalProperty> findAllByMonthlyRent(BigDecimal rentPerMonth) {
        return rentalRepo.findAllByRentPerMonth(rentPerMonth);
    }

    @Override
    public Set<RentalProperty> findAllByMinTenancy(int minTenancy) {
        return rentalRepo.findAllByMinTenancy(minTenancy);
    }

    @Override
    public Set<RentalProperty> findByNumBedrooms(int numBedrooms) {
        return rentalRepo.findAllByNumBedrooms(numBedrooms);
    }

    @Override
    public RentalProperty save(long proprietorId, RentalProperty rentalProperty) throws PropertyNotFoundException {
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));
        rentalProperty.setProprietor(proprietor);

        RentalProperty property = rentalRepo.save(rentalProperty);

        proprietor.addRentalProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        return rentalRepo.save(rentalProperty);
    }

    @Override
    public RentalProperty findById(long propertyId) throws PropertyNotFoundException {
        return rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
    }

    @Override
    public RentalProperty updateById(long propertyId, RentalProperty rentalPropertyDTOIn) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        Proprietor proprietor = property.getProprietor();

        modelMapper.map(rentalPropertyDTOIn, property);
        property.setId(propertyId);
        property.setProprietor(proprietor);

        proprietor.removeRentalProperty(property);
        proprietor.addRentalProperty(property);

        RentalProperty savedProperty = rentalRepo.save(property);
        proprietorService.updatePropertyDetails(proprietor);

        return savedProperty;
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        Proprietor proprietor = property.getProprietor();
        proprietor.removeRentalProperty(property);
        rentalRepo.delete(property);
        proprietorService.updatePropertyDetails(proprietor);
    }

    @Override
    public void deleteAddressById(long propertyId) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        property.setAddress(null);

        rentalRepo.save(property);
    }
}
