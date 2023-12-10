package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
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
    public RentalProperty save(long proprietorId, RentalProperty rentalProperty) throws ProprietorNotFoundException {
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

        return savedProperty;
    }

    @Override
    public RentalProperty findById(long propertyId) throws PropertyNotFoundException {
        return rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
    }

    @Override
    public RentalProperty updateById(long propertyId, RentalProperty rentalPropertyDTOIn) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // coge el objeto Proprietor
        Proprietor proprietor = property.getProprietorRental();

        // mapea el objeto actualizado en el original
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

        return updatedProperty;
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // actualiza el objeto Proprietor
        Proprietor proprietor = property.getProprietorRental();
        proprietor.removeRentalProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        rentalRepo.delete(property);
    }

    @Override
    public void deleteAddressById(long propertyId) throws PropertyNotFoundException {
        RentalProperty property = rentalRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        property.setAddress(null);
        rentalRepo.save(property);
    }
}
