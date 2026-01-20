package com.syncstate.apps.SyncTracker.controllers;

import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.requests.CreateClientRequest;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vi/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @RequestMapping(value="/create-client", method = RequestMethod.POST)
    public ResponseEntity createClient(@RequestBody(required = true) CreateClientRequest createClientRequest)
    {
        try {
            System.out.println(createClientRequest.getClientName());
            SmartBankingResponse smartBankingResponse = clientService.createClient(createClientRequest);
            return ResponseEntity.ok().body(smartBankingResponse);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
    }
}
