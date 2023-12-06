package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.ClientDTOIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * ClientController - controller para manejar los datos sobre entidades de un cliente ('client').
 *
 * @author Philippa Bather
 */

@Validated
@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/users/clients")
    public ResponseEntity<Set<ClientDTOOut>> getAllClients(@RequestParam(value = "surname", defaultValue = "")
                                                            String surname,
                                                            @RequestParam(value = "name", defaultValue = "") String name,
                                                            @RequestParam(value = "dob", defaultValue = "") String dob) {

        Set<ClientDTOOut> clients = new HashSet<>();

        if (surname.trim().equals("") && name.trim().equals("") && dob.trim().equals("")) {
            clients = clientService.findAll();
        } else if (surname.trim().length() >= 1) {
            clients = clientService.findAllBySurname(surname);
        } else if (name.trim().length() >= 1) {
            clients = clientService.findAllByName(name);
        } else if (dob.trim().length() >= 1) {
            clients = clientService.findAllByDOB(LocalDate.parse(dob));
        }

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping("/users/clients")
    public ResponseEntity<ClientDTOOut> createClient(@Valid @RequestBody ClientDTOIn clientDTOIn) {
        ClientDTOOut clientDTOOut = clientService.save(clientDTOIn);
        return new ResponseEntity<>(clientDTOOut, HttpStatus.CREATED);
    }

    @GetMapping("/users/clients/{clientId}")
    public ResponseEntity<ClientDTOOut> findClientById(@PathVariable long clientId) throws ClientNotFoundException {
        ClientDTOOut client = clientService.findById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @PutMapping("/users/clients/{clientId}")
    public ResponseEntity<ClientDTOOut> updateClientById(@PathVariable long clientId, @Valid @RequestBody ClientDTOIn clientDTOIn)
            throws ClientNotFoundException {
        ClientDTOOut clientDTOOut = clientService.updateById(clientId, clientDTOIn);
        return new ResponseEntity<>(clientDTOOut, HttpStatus.OK);
    }

    @DeleteMapping("/users/clients/{clientId}")
    public ResponseEntity<Void> deleteClientById(@PathVariable long clientId) throws ClientNotFoundException {
        clientService.deleteById(clientId);
        return ResponseEntity.noContent().build(); // 204
    }
}
