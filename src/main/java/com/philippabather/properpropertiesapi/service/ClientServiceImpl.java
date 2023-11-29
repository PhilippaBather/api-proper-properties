package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ClientDTOIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.model.Client;
import com.philippabather.properpropertiesapi.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * ClientServiceImpl - implementa la interfaz ClientService; maneja información sobre la entidad Client ('cliente').
 *
 * @author Philippa Bather
 */

@Service
public class ClientServiceImpl implements ClientService {

    private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepo;
    private final ModelMapper modelMapper;

    public ClientServiceImpl(ClientRepository clientRepo, ModelMapper modelMapper) {
        this.clientRepo = clientRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Set<ClientDTOOut> findAll() {
        Set<Client> clients = clientRepo.findAll();
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllBySurname(String surname) {
        Set<Client> clients = clientRepo.findAllBySurname(surname);
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllByName(String name) {
        Set<Client> clients = clientRepo.findAllByName(name);
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllByDOB(LocalDate dob) {
        Set<Client> clients = clientRepo.findAllByDob(dob);
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public ClientDTOOut save(ClientDTOIn clientDTOIn) {
        // mapea la entrada a un objeto Client
        Client client = new Client();
        modelMapper.map(clientDTOIn, client);
        Client savedClient = clientRepo.save(client);

        // mapea el nuevo cliente guardado al DTO de salida
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(savedClient, clientDTOOut);
        return clientDTOOut;
    }

    @Override
    public ClientDTOOut findById(long clientId) throws ClientNotFoundException {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(client, clientDTOOut);
        return clientDTOOut;
    }

    @Override
    public ClientDTOOut updateClientById(long clientId, ClientDTOIn clientDTOIn) throws ClientNotFoundException {
        // comprueba si el cliente existe
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));

        // mapea los cambios en el objeto de cliente pre-existente y lo guarda
        modelMapper.map(clientDTOIn, client);
        client.setId(clientId);
        Client clientUpdated = clientRepo.save(client);

        // mapea el objeto cliente a el ClientDTOOut
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(clientUpdated, clientDTOOut);

        return clientDTOOut;
    }

    @Override
    public void deleteById(long clientId) throws ClientNotFoundException {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        clientRepo.delete(client);
    }

    private Set<ClientDTOOut> convertToClientDTOOutSet(Set<Client> clients) {
        Set<ClientDTOOut> clientsDTOOut = new HashSet<>();

        for (Client client :
                clients) {
            ClientDTOOut clientDTOOut = new ClientDTOOut();
            logger.info(client.toString());
            modelMapper.map(client, clientDTOOut);
            clientsDTOOut.add(clientDTOOut);
        }
        return clientsDTOOut;
    }
}
