package com.syncstate.apps.SyncTracker.service;


import com.syncstate.apps.SyncTracker.controllers.UserController;
import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.*;
import com.syncstate.apps.SyncTracker.models.requests.*;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private HttpServletRequest request;
    public SmartBankingResponse createClient(CreateClientRequest createClientRequest) throws SyncTrackerException {

        Client client = this.clientRepository.getClientByClientName(createClientRequest.getClientName());
        if(client==null)
            throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");

        client = new Client();
        client.setClientName(createClientRequest.getClientName());
        client = (Client)this.clientRepository.save(client);

        if(client==null)
            throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");

        CreateUserRequest createUserRequest = createClientRequest.getCreateUserRequest();
        UserController userController = new UserController();
        ResponseEntity re = userController.createNewUser(createUserRequest);
        if(re.getStatusCode().equals(HttpStatusCode.valueOf(200)))
        {
            CreateUserResponse createUserResponse = (CreateUserResponse)re.getBody();
            Map responseObject = new HashMap();
            responseObject.put("client", client);

            SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
            smartBankingResponse.setMessage("Your organization has been created. Please proceed to sign in and start using our services.");
            smartBankingResponse.setStatusCode(0);
            smartBankingResponse.setResponseObject(responseObject);

            return smartBankingResponse;

        }
        throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");


    }

}
