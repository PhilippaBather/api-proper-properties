package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.ClientDTOInIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.model.Client;
import com.philippabather.properpropertiesapi.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Set<ClientDTOOut>> findAllClients() {
        // TODO - query params
        Set<ClientDTOOut> clients = clientService.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @PostMapping("/users/clients")
    public ResponseEntity<Client> createClient(@Valid @RequestBody ClientDTOInIn clientDTOIn) {
        // TODO - must return a ClientDTOOut
        Client client = clientService.save(clientDTOIn);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @GetMapping("/users/clients/{clientId}")
    public ResponseEntity<ClientDTOOut> findClientById(@PathVariable long clientId) throws ClientNotFoundException {
        ClientDTOOut client = clientService.findById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}
