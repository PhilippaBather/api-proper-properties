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
 * ClientServiceImpl - implementa la interfaz de servicio ClientService; maneja información sobre la entidad Client ('cliente').
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
        logger.info("start: ClientServiceImpl_findAll");
        Set<Client> clients = clientRepo.findAll();
        logger.info("end: ClientServiceImpl_findAll");
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllBySurname(String surname) {
        logger.info("start: ClientServiceImpl_findAllBySurname");
        Set<Client> clients = clientRepo.findAllBySurname(surname);
        logger.info("end: ClientServiceImpl_findAllBySurname");
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllByName(String name) {
        logger.info("start: ClientServiceImpl_findAllByName");
        Set<Client> clients = clientRepo.findAllByName(name);
        logger.info("end: ClientServiceImpl_findAllByName");
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public Set<ClientDTOOut> findAllByDOB(LocalDate dob) {
        logger.info("start: ClientServiceImpl_findAllByDOB");
        Set<Client> clients = clientRepo.findAllByDob(dob);
        logger.info("end: ClientServiceImpl_findAllByDOB");
        return convertToClientDTOOutSet(clients);
    }

    @Override
    public ClientDTOOut save(ClientDTOIn clientDTOIn) {
        logger.info("start: ClientServiceImpl_save");
        // mapea la entrada a un objeto Client
        Client client = new Client();
        modelMapper.map(clientDTOIn, client);
        Client savedClient = clientRepo.save(client);

        // mapea el nuevo cliente guardado al DTO de salida
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(savedClient, clientDTOOut);
        logger.info("end: ClientServiceImpl_save");
        return clientDTOOut;
    }

    @Override
    public ClientDTOOut findById(long clientId) throws ClientNotFoundException {
        logger.info("start: ClientServiceImpl_findById");
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(client, clientDTOOut);
        logger.info("end: ClientServiceImpl_findById");
        return clientDTOOut;
    }

    @Override
    public ClientDTOOut updateById(long clientId, ClientDTOIn clientDTOIn) throws ClientNotFoundException {
        logger.info("start: ClientServiceImpl_updateById");
        // comprueba si el cliente existe
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));

        // mapea los cambios en el objeto de cliente pre-existente y lo guarda
        modelMapper.map(clientDTOIn, client);
        client.setId(clientId);
        Client updatedClient = clientRepo.save(client);

        // mapea el objeto cliente a el ClientDTOOut
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(updatedClient, clientDTOOut);

        logger.info("end: ClientServiceImpl_updateById");
        return clientDTOOut;
    }

    @Override
    public void deleteById(long clientId) throws ClientNotFoundException {
        logger.info("start: ClientServiceImpl_deleteById");
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        clientRepo.delete(client);
        logger.info("end: ClientServiceImpl_deleteById");
    }

    private Set<ClientDTOOut> convertToClientDTOOutSet(Set<Client> clients) {
        logger.info("start: ClientServiceImpl_convertToClientDTOOutSet");
        Set<ClientDTOOut> clientsDTOOut = new HashSet<>();

        for (Client client :
                clients) {
            ClientDTOOut clientDTOOut = new ClientDTOOut();
            logger.info(client.toString());
            modelMapper.map(client, clientDTOOut);
            clientsDTOOut.add(clientDTOOut);
        }
        logger.info("end: ClientServiceImpl_convertToClientDTOOutSet");
        return clientsDTOOut;
    }
}
