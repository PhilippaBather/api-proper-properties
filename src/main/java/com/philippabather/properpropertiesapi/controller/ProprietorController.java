package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.ProprietorDTOIn;
import com.philippabather.properpropertiesapi.dto.ProprietorDTOOut;
import com.philippabather.properpropertiesapi.exception.ProprietorNotFoundException;
import com.philippabather.properpropertiesapi.service.ProprietorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ProprietorController {

    private ProprietorService proprietorService;

    public ProprietorController(ProprietorService proprietorService) {
        this.proprietorService = proprietorService;
    }

    @GetMapping("/users/proprietors")
    public ResponseEntity<Set<ProprietorDTOOut>> getAllProprietors(@RequestParam(value = "surname", defaultValue = "")
                                                                   String surname,
                                                                   @RequestParam(value = "isAgency", defaultValue = "false")
                                                                   boolean isAgency,
                                                                   @RequestParam(value = "numProperties", defaultValue = "0")
                                                                   int numProperties) {
        Set<ProprietorDTOOut> proprietors = new HashSet<>();

        if (surname.trim().equals("") && !isAgency && numProperties == 0) {
            proprietors = proprietorService.findAll();
        } else if (surname.trim().length() >= 1) {
            proprietors = proprietorService.findAllBySurname(surname);
        } else if (isAgency) {
            proprietors = proprietorService.findAllByIsAgency(isAgency);
        } else if (numProperties > 0) {
            proprietors = proprietorService.findAllByNumProperties(numProperties);
        }

        return new ResponseEntity<>(proprietors, HttpStatus.OK);
    }

    @PostMapping("/users/proprietors")
    public ResponseEntity<ProprietorDTOOut> createProprietor(@Valid @RequestBody ProprietorDTOIn proprietorDTOIn) {
        ProprietorDTOOut proprietorDTOOut = proprietorService.save(proprietorDTOIn);
        return new ResponseEntity<>(proprietorDTOOut, HttpStatus.CREATED);
    }

    @GetMapping("/users/proprietors/{proprietorId}")
    public ResponseEntity<ProprietorDTOOut> findProprietorById(@PathVariable long proprietorId) throws ProprietorNotFoundException {
        ProprietorDTOOut proprietor = proprietorService.findById(proprietorId);
        return new ResponseEntity<>(proprietor, HttpStatus.OK);
    }

    @PutMapping("/users/proprietors/{proprietorId}")
    public ResponseEntity<ProprietorDTOOut> updateProprietorById(@PathVariable long proprietorId, @Valid @RequestBody ProprietorDTOIn proprietorDTOIn)
            throws ProprietorNotFoundException {
        ProprietorDTOOut proprietor = proprietorService.updateById(proprietorId, proprietorDTOIn);
        return new ResponseEntity<>(proprietor, HttpStatus.OK);
    }

    @DeleteMapping("/users/proprietors/{proprietorId}")
    public ResponseEntity<Void> deleteProprietorById(@PathVariable long proprietorId) throws ProprietorNotFoundException {
        proprietorService.deleteById(proprietorId);
        return ResponseEntity.noContent().build();
    }

}
