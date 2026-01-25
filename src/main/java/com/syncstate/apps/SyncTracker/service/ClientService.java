package com.syncstate.apps.SyncTracker.service;


import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.*;
import com.syncstate.apps.SyncTracker.models.enums.ClientUserStatus;
import com.syncstate.apps.SyncTracker.models.requests.*;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IClientDomainRepository clientDomainRepository;

    @Autowired
    private IClientUserRepository clientUserRepository;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ITokenRepository tokenRepository;

    public SmartBankingResponse createClient(UserService userService, CreateClientRequest createClientRequest) throws SyncTrackerException {

        Client client = this.clientRepository.getClientByClientName(createClientRequest.getClientName());
        if(client!=null)
            throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");

        client = new Client();
        client.setClientName(createClientRequest.getClientName());
        client.setClientCode(RandomStringUtils.randomAlphanumeric(16).toUpperCase());
        client = (Client)this.clientRepository.save(client);

        if(client==null)
            throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");

        CreateUserRequest createUserRequest = createClientRequest.getCreateUserRequest();
        Map map = userService.createNewUser(createUserRequest);
        if(map!=null && map.get("user")!=null)
        {
            User user = (User)map.get("user");
            Token token = (Token)map.get("token");
            ClientUser clientUser = new ClientUser();
            clientUser.setClientId(client.getClientId());
            clientUser.setUserId(user.getUserId());
            clientUser.setClientUserStatus(ClientUserStatus.INACTIVE);
            clientUser = (ClientUser)this.clientUserRepository.save(clientUser);

            token.setTokenOwnedByEntityId(clientUser.getClientUserId());
            this.tokenRepository.save(token);


            Map responseObject = new HashMap();
            responseObject.put("client", client);
            responseObject.put("clientUser", clientUser);
            responseObject.put("token", token);

            SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
            smartBankingResponse.setMessage("Your organization has been created. Please proceed to provide the activation code sent to your email address.");
            smartBankingResponse.setStatusCode(0);
            smartBankingResponse.setResponseObject(responseObject);

            return smartBankingResponse;

        }
        throw new SyncTrackerException("Invalid employer name provided. Please provide another employer name.");


    }

    public SmartBankingResponse getClientByDomain(String domain) throws SyncTrackerException {
        Client client = this.clientDomainRepository.getClientByDomain(domain);


        Map responseObject = new HashMap();
        responseObject.put("client", client);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setMessage("Client was found.");
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }
}
