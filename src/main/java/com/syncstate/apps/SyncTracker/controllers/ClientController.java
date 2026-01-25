package com.syncstate.apps.SyncTracker.controllers;

import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.requests.CreateClientRequest;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.service.ClientService;
import com.syncstate.apps.SyncTracker.service.EmploymentService;
import com.syncstate.apps.SyncTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vi/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    EmploymentService employmentService;


    @Autowired
    private UserService userService;


    @RequestMapping(value="/create-client", method = RequestMethod.POST)
    public ResponseEntity createClient(@RequestBody(required = true) CreateClientRequest createClientRequest)
    {
        try {
            System.out.println(createClientRequest.getClientName());
            SmartBankingResponse smartBankingResponse = clientService.createClient(userService, createClientRequest);
            return ResponseEntity.ok().body(smartBankingResponse);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
    }


    @RequestMapping(value="/get-client-by-domain/{domain}", method = RequestMethod.GET)
    public ResponseEntity getClientByDomain(@PathVariable(required = true) String domain)
    {
        try {
            System.out.println(domain);
            SmartBankingResponse smartBankingResponse = clientService.getClientByDomain(domain);
            return ResponseEntity.ok().body(smartBankingResponse);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
    }
}
