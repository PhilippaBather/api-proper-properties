package com.philippabather.properpropertiesapi.service;

import com.philippabather.properpropertiesapi.dto.ClientDTOInIn;
import com.philippabather.properpropertiesapi.dto.ClientDTOOut;
import com.philippabather.properpropertiesapi.exception.ClientNotFoundException;
import com.philippabather.properpropertiesapi.model.Client;
import com.philippabather.properpropertiesapi.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    @Override
    public Client save(ClientDTOInIn clientDTOIn) {
        Client client = new Client();
        modelMapper.map(clientDTOIn, client);
        return clientRepo.save(client);
    }

    @Override
    public ClientDTOOut findById(long clientId) throws ClientNotFoundException {
        Client client = clientRepo.findById(clientId).orElseThrow(() -> new ClientNotFoundException(clientId));
        ClientDTOOut clientDTOOut = new ClientDTOOut();
        modelMapper.map(client, clientDTOOut);
        return clientDTOOut;
    }
}
