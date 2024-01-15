package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.SaleDTOOut;
import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.PropertyStatus;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.repository.ProprietorRepository;
import com.philippabather.properpropertiesapi.repository.SalePropertyRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class SalePropertyServiceImpl implements SalePropertyService{

    private final Logger logger = LoggerFactory.getLogger(SalePropertyServiceImpl.class);
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
    public Set<SaleDTOOut> findAll() {
        logger.info("start: SalePropertyService_findAll");
        Set<SaleProperty> saleProperties = saleRepo.findAll();
        logger.info("end: SalePropertyService_findAll");
        return convertToSaleDTOOutSet(saleProperties);
    }

    @Override
    public Set<SaleDTOOut> findAllByPrice(BigDecimal price) {
        logger.info("start: SalePropertyService_findAllByPrice");
        Set<SaleProperty> saleProperties = saleRepo.findAllByPrice(price);
        logger.info("end: SalePropertyService_findAllByPrice");
        return convertToSaleDTOOutSet(saleProperties);
    }

    @Override
    public Set<SaleDTOOut> findAllByConstructionDate(LocalDate constructionDate) {
        logger.info("start: SalePropertyService_findAllByConstructionDate");
        Set<SaleProperty> saleProperties = saleRepo.findAllByConstructionDate(constructionDate);
        logger.info("end: SalePropertyService_findAllByConstructionDate");
        return convertToSaleDTOOutSet(saleProperties);
    }

    @Override
    public Set<SaleDTOOut> findAllByMetresSqr(int metresSqr) {
        logger.info("start: SalePropertyService_findAllByMetresSqr");
        Set<SaleProperty> saleProperties = saleRepo.findAllByMetresSqr(metresSqr);
        logger.info("end: SalePropertyService_findAllByMetresSqr");
        return convertToSaleDTOOutSet(saleProperties);
    }

    @Override
    public SaleDTOOut save(long proprietorId, SaleProperty saleProperty) throws ProprietorNotFoundException {
        logger.info("start: SalePropertyService_save");
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

        logger.info("end: SalePropertyService_save");
        return mapToSaleDTOOut(savedProperty);
      }

    @Override
    public SaleDTOOut findById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: SalePropertyService_findById");
        SaleProperty saleProperty = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        logger.info("end: SalePropertyService_findById");
        return mapToSaleDTOOut(saleProperty);
    }

    @Override
    public SaleDTOOut updateById(long propertyId, SaleProperty saleProperty) throws PropertyNotFoundException {
        logger.info("start: SalePropertyService_updateById");
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

        logger.info("end: SalePropertyService_updateById");
        return mapToSaleDTOOut(updatedProperty);
    }

    @Override
    public void deleteById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: SalePropertyService_deleteById");
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));

        // actualiza el objeto Proprietor
        Proprietor proprietor = property.getProprietorSale();
        proprietor.removeSaleProperty(property);
        proprietorService.updatePropertyDetails(proprietor);

        saleRepo.delete(property);
        logger.info("end: SalePropertyService_deleteById");
    }

    @Override
    public void deleteAddressById(long propertyId) throws PropertyNotFoundException {
        logger.info("start: SalePropertyService_deleteAddressById");
        SaleProperty property = saleRepo.findById(propertyId).orElseThrow(() -> new PropertyNotFoundException(propertyId));
        property.setAddress(null);
        saleRepo.save(property);
        logger.info("end: SalePropertyService_deleteAddressById");
    }

    private Set<SaleDTOOut> convertToSaleDTOOutSet(Set<SaleProperty> rentalProperties) {
        logger.info("start: SalePropertyService_convertToSaleDTOOutSet");
        Set<SaleDTOOut> salesDTOOut = new HashSet<>();

        for (SaleProperty sale :
                rentalProperties) {
            SaleDTOOut saleDTOOut = new SaleDTOOut();
            modelMapper.map(sale, saleDTOOut);
            salesDTOOut.add(saleDTOOut);
        }

        logger.info("end: SalePropertyService_convertToSaleDTOOutSet");
        return salesDTOOut;
    }

    private SaleDTOOut mapToSaleDTOOut(SaleProperty saleProperty){
        logger.info("start: SalePropertyService_mapToSaleDTOOut");
        SaleDTOOut saleDTOOut = new SaleDTOOut();
        modelMapper.map(saleProperty, saleDTOOut);
        logger.info("end: SalePropertyService_mapToSaleDTOOut");
        return saleDTOOut;
    }
}
