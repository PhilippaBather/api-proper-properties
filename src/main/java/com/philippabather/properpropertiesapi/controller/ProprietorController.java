package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.InvalidLoginException;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.model.Proprietor;
import com.philippabather.properpropertiesapi.service.ProprietorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * ProprietorController - controller para manejar los datos sobre entidaes de un Proprietor ('Propietario').
 *
 * @author Philippa Bather
 */

@Validated
@RestController
public class ProprietorController {

    private static final Logger logger = LoggerFactory.getLogger(ProprietorController.class);
    private ProprietorService proprietorService;

    public ProprietorController(ProprietorService proprietorService) {
        this.proprietorService = proprietorService;
    }

    @GetMapping("/users/proprietors")
    public ResponseEntity<Set<ProprietorDTOOut>> getAllProprietors(@RequestParam(value = "surname", defaultValue = "")
                                                                   String surname,
                                                                   @RequestParam(value = "telephone", defaultValue = "")
                                                                   String telephone,
                                                                   @RequestParam(value = "numProperties", defaultValue = "0")
                                                                   int numProperties) {
        logger.info("start: ProprietorController_getAllProprietors");

        Set<ProprietorDTOOut> proprietors = new HashSet<>();

        if (surname.trim().equals("") && telephone.trim().equals("") && numProperties == 0) {
            proprietors = proprietorService.findAll();
        } else if (surname.trim().length() >= 1) {
            proprietors = proprietorService.findAllBySurname(surname);
        } else if (telephone.trim().length() >= 9) { // tamaño de número de teléfono
            proprietors = proprietorService.findAllByTelephone(telephone);
        } else if (numProperties > 0) {
            proprietors = proprietorService.findAllByNumProperties(numProperties);
        }

        logger.info("end: ProprietorController_getAllProprietors");
        return new ResponseEntity<>(proprietors, HttpStatus.OK);
    }

    @GetMapping("/users/proprietors/{proprietorId}")
    public ResponseEntity<ProprietorDTOOut> findProprietorById(@PathVariable long proprietorId) throws ProprietorNotFoundException {
        logger.info("start: ProprietorController_findProprietorById");
        ProprietorDTOOut proprietor = proprietorService.findById(proprietorId);
        logger.info("end: ProprietorController_findProprietorById");
        return new ResponseEntity<>(proprietor, HttpStatus.OK);
    }

    // Endpoint reemplazado por "/token" en AuthController
//    @PostMapping("/users/proprietors")
//    public ResponseEntity<ProprietorDTOOut> createProprietor(@Valid @RequestBody ProprietorDTOIn proprietorDTOIn) {
//        ProprietorDTOOut proprietorDTOOut = proprietorService.save(proprietorDTOIn);
//        return new ResponseEntity<>(proprietorDTOOut, HttpStatus.CREATED);
//    }

    @GetMapping("/users/proprietors/secured/{username}")
    public ResponseEntity<ProprietorDTOOut> findProprietorByUsername(@PathVariable String username)
            throws InvalidLoginException {
        logger.info("start: ProprietorController_findProprietorByUsername");
        Proprietor proprietor = proprietorService.findByUsername(username);
        ProprietorDTOOut proprietorDTOOut = proprietorService.getProprietorDTO(proprietor);
        logger.info("end: ProprietorController_findProprietorByUsername");
        return new ResponseEntity<>(proprietorDTOOut, HttpStatus.OK);
    }

    @PutMapping("/users/proprietors/secured/{proprietorId}")
    public ResponseEntity<ProprietorDTOOut> updateProprietorById(@PathVariable long proprietorId, @Valid @RequestBody ProprietorDTOIn proprietorDTOIn)
            throws ProprietorNotFoundException {
        logger.info("start: ProprietorController_updateProprietorById");
        ProprietorDTOOut proprietor = proprietorService.updateById(proprietorId, proprietorDTOIn);
        logger.info("end: ProprietorController_updateProprietorById");
        return new ResponseEntity<>(proprietor, HttpStatus.OK);
    }

    @DeleteMapping("/users/proprietors/secured/{proprietorId}")
    public ResponseEntity<Void> deleteProprietorById(@PathVariable long proprietorId) throws ProprietorNotFoundException {
        logger.info("start: ProprietorController_deleteProprietorById");
        proprietorService.deleteById(proprietorId);
        logger.info("end: ProprietorController_deleteProprietorById");
        return ResponseEntity.noContent().build();
    }

}
