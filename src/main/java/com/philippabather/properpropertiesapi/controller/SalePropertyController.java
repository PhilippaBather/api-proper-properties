package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.SaleDTOOut;
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
    public ResponseEntity<Set<SaleDTOOut>> getAllSaleProperties(@RequestParam(name = "price", defaultValue = "0.00")
                                                                  BigDecimal price,
                                                                @RequestParam(name = "constructionDate", defaultValue = "")
                                                                  String constructionDate,
                                                                @RequestParam(name = "metresSqr", defaultValue = "0")
                                                                  int metresSqr) {
        Set<SaleDTOOut> properties = new HashSet<>();

        if (price.toString().equals("0.00") && constructionDate.equals("") && metresSqr == 0) {
            properties = saleService.findAll();
        } else if (!price.toString().equals("0.00")) {
            properties = saleService.findAllByPrice(price);
        } else if (constructionDate.trim().length() >= 10) {  // 10 porque Local date min 10 chars, por ej. 1999-10-10
            properties = saleService.findAllByConstructionDate(LocalDate.parse(constructionDate));
        } else if (metresSqr > 10) {
            properties = saleService.findAllByMetresSqr(metresSqr);
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @PostMapping("/properties/sale/{proprietorId}")
    public ResponseEntity<SaleDTOOut> createSaleProperty(@PathVariable long proprietorId,
                                                           @Valid @RequestBody SaleProperty saleProperty)
            throws ProprietorNotFoundException {
        SaleDTOOut property = saleService.save(proprietorId, saleProperty);
        return new ResponseEntity<>(property, HttpStatus.CREATED);
    }

    @GetMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleDTOOut> getPropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        SaleDTOOut property = saleService.findById(propertyId);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PutMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleDTOOut> updatePropertyById(@PathVariable long propertyId,
                                                           @Valid @RequestBody SaleProperty saleProperty)
            throws PropertyNotFoundException {
        SaleDTOOut property = saleService.updateById(propertyId, saleProperty);
        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @DeleteMapping("/properties/sale/{propertyId}")
    public ResponseEntity<SaleProperty> deletePropertyById(@PathVariable long propertyId) throws PropertyNotFoundException {
        saleService.deleteById(propertyId);
        return ResponseEntity.noContent().build();
    }
}
