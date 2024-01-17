package com.philippabather.properpropertiesapi.repository;

import com.philippabather.properpropertiesapi.model.SaleProperty;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

/**
 * SalePropertyRepository - el repositorio para manejar la entidad SaleProperty ('inmueble para vender').
 *
 * @author Philippa Bather
 */

@Repository
public interface SalePropertyRepository extends CrudRepository<SaleProperty, Long> {

    Set<SaleProperty> findAll();
    Set<SaleProperty> findAllByPrice(BigDecimal price);
    Set<SaleProperty> findAllByConstructionDate(LocalDate constructionDate);
    Set<SaleProperty> findAllByMetresSqr(int metresSqr);
    Optional<SaleProperty> findById(long propertyId);
}
