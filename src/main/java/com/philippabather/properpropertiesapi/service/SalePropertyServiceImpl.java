package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import com.philippabather.properpropertiesapi.repository.SalePropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Service
public class SalePropertyServiceImpl implements SalePropertyService{

    private final ProprietorRepository proprietorRepo;
    private final ProprietorService proprietorService;
    private final SalePropertyRepository saleRepo;
    private ModelMapper modelMapper;

    public SalePropertyServiceImpl(ProprietorRepository proprietorRepo, ProprietorService proprietorService,
                                   SalePropertyRepository saleRepo, ModelMapper modelMapper) {
        this.proprietorRepo = proprietorRepo;
        this.proprietorService = proprietorService;
        this.saleRepo = saleRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<SaleProperty> findAll() {
        return saleRepo.findAll();
    }

    @Override
    public Set<SaleProperty> findAllByPrice(BigDecimal price) {
        return saleRepo.findAllByPrice(price);
    }

    @Override
    public Set<SaleProperty> findAllByConstructionDate(LocalDate constructionDate) {
        return saleRepo.findAllByConstructionDate(constructionDate);
    }

    @Override
    public Set<SaleProperty> findAllByIsLeasehold(boolean isLeasehold) {
        return saleRepo.findAllByIsLeasehold(isLeasehold);
    }

    @Override
    public SaleProperty save(long proprietorId, SaleProperty saleProperty) throws ProprietorNotFoundException {
        // coge el objeto de Proprietor o lanza una excepción
        Proprietor proprietor = proprietorRepo.findById(proprietorId).orElseThrow(() -> new ProprietorNotFoundException(proprietorId));

        // establece los campos de Proprietor y PropertyStatus
        saleProperty.setProprietorSale(proprietor);
        saleProperty.setPropertyStatus(PropertyStatus.SALE);  // establece por defecto

        // guarda el inmueble
        SaleProperty savedProperty = saleRepo.save(saleProperty);

        // actualiza las detalles del inmueble en el objeto de Proprietor y guarda dicho objeto
        proprietor.addSaleProperty(savedProperty);
        proprietorService.updatePropertyDetails(proprietor);

        return savedProperty;
      }

    @Override
    public SaleProperty findById(long propertyId) throws PropertyNotFoundException {
        return saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
    }

    @Override
    public SaleProperty updateById(long propertyId, SaleProperty saleProperty) throws PropertyNotFoundException {
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // coge el objeto Proprietor
        Proprietor proprietor = property.getProprietorSale();

        // mapea el objeto actualizado al original
        modelMapper.map(saleProperty, property);
        // establace campos
        property.setId(propertyId);
        property.setProprietorSale(proprietor);
        property.setPropertyStatus(PropertyStatus.SALE);  // establece por defecto
        // guardalo
        SaleProperty updatedProperty = saleRepo.save(property);

        // actualiza el objeto Proprietor
        proprietor.removeSaleProperty(property);
        proprietor.addSaleProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        return updatedProperty;
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // actualiza el objeto Proprietor
        Proprietor proprietor = property.getProprietorSale();
        proprietor.removeSaleProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        saleRepo.delete(property);
    }

    @Override
    public void deleteAddressById(long propertyId) throws PropertyNotFoundException {
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        property.setAddress(null);
        saleRepo.save(property);
    }
}
