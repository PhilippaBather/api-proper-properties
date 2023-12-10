package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.exception.PropertyNotFoundException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.SaleProperty;
import com.philippabather.properpropertiesapi.service.SalePropertyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * SalePropertyController - controller para manejar los datos sobre entidaes de inmuebles para vender ('Sale Property').
 *
 * @author Philippa Bather
 */
@Validated
@RestController
public class SalePropertyController {

    private final SalePropertyService saleService;

    public SalePropertyController(SalePropertyService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/properties/sale")
    public ResponseEntity<Set<SaleProperty>> getAllSaleProperties(@RequestParam(name = "price", defaultValue = "0.00")
                                                                  BigDecimal price,
                                                                  @RequestParam(name = "constructionDate", defaultValue = "")
                                                                  String constructionDate,
                                                                  @RequestParam(name = "isLeasehold", defaultValue = "false")
                                                                  boolean isLeasehold) {
        Set<SaleProperty> properties = new HashSet<>();

        if (price.toString().equals("0.00") && constructionDate.equals("") && !isLeasehold) {
            properties = saleService.findAll();
        } else if (!price.toString().equals("0.00")) {
            properties = saleService.findAllByPrice(price);
        } else if (constructionDate.trim().length() >= 10) {  // 10 porque Local date min 10 chars, por ej. 1999-10-10
            properties = saleService.findAllByConstructionDate(LocalDate.parse(constructionDate));
        } else if (isLeasehold) {
            properties = saleService.findAllByIsLeasehold(isLeasehold);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("/properties/sale/{proprietorId}")
    public ResponseEntity<SaleProperty> createSaleProperty(@PathVariable long proprietorId,
                                                           @Valid @RequestBody SaleProperty saleProperty)
            throws ProprietorNotFoundException {
        SaleProperty property = saleService.save(proprietorId, saleProperty);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleProperty> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        SaleProperty property = saleService.findById(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleProperty> updatePropertyById(@PathVariable long propertyId,
                                                           @Valid @RequestBody SaleProperty saleProperty)
            throws PropertyNotFoundException {
        SaleProperty property = saleService.updateById(propertyId, saleProperty);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @DeleteMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleProperty> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        saleService.deleteById(propertyId);
        return ResponseEntity.noContent().build();
    }
}
