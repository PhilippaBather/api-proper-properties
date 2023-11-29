package com.philippabather.properpropertiesapi.controller;

import com.philippabather.properpropertiesapi.dto.ClientDTOInIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.model.Client;
import com.philippabather.properpropertiesapi.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * ClientController - controller para manejar los datos sobre la(s) entidades de un cliente ('client').
 *
 * @author Philippa Bather
 */

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
    public ResponseEntity<Client> createClient(@RequestBody ClientDTOInIn clientDTOIn) {
        Client client = clientService.save(clientDTOIn);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @GetMapping("/users/clients/{clientId}")
    public ResponseEntity<Client> findClientById(@PathVariable long clientId) throws ClientNotFoundException {
        Client client = clientService.findById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);

    }
}
