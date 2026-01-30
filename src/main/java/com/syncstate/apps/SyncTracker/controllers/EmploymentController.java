package com.syncstate.apps.SyncTracker.controllers;



import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.requests.*;
import com.syncstate.apps.SyncTracker.models.responses.GetEmployeeTimesheetResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.models.responses.TimesheetClockInResponse;
import com.syncstate.apps.SyncTracker.service.EmploymentService;
import com.syncstate.apps.SyncTracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;

@Controller
@RequestMapping("/api/v1/employment")
public class EmploymentController {


    @Autowired
    private EmploymentService employmentService;



    @RequestMapping(value = "/get-employees/{clientCode}", method = RequestMethod.GET)
    public ResponseEntity getEmployees(@PathVariable String clientCode )
    {
        SmartBankingResponse getEmployeeResponse = null;
        try {

            getEmployeeResponse = employmentService.getEmployeeList(clientCode);
            return ResponseEntity.ok().body(getEmployeeResponse);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/create-new-employee", method = RequestMethod.POST)
    public ResponseEntity createNewEmployee(@RequestBody CreateNewEmployeeRequest createNewEmployeeRequest)
    {
        SmartBankingResponse createNewEmployeeResponse = null;
        try {
            createNewEmployeeResponse = employmentService.createNewEmployee(createNewEmployeeRequest);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(createNewEmployeeResponse);
    }

    @RequestMapping(value = "/create-new-employee-contract", method = RequestMethod.POST)
    public ResponseEntity createNewEmployeeContract(@RequestBody CreateNewEmployeeContractRequest createNewEmployeeContractRequest)
    {
        SmartBankingResponse createNewEmployeeContractResponse = employmentService.createNewEmployeeContract(createNewEmployeeContractRequest);
        return ResponseEntity.ok().body(createNewEmployeeContractResponse);
    }

    @RequestMapping(value = "/create-new-employee-contract-template", method = RequestMethod.POST)
    public ResponseEntity createNewEmployeeContractTemplate(@RequestBody CreateNewEmployeeContractTemplateRequest createNewEmployeeContractTemplateRequest)
    {
        SmartBankingResponse createNewEmployeeContractTemplateResponse = employmentService.createNewEmployeeContractTemplate(createNewEmployeeContractTemplateRequest);
        return ResponseEntity.ok().body(createNewEmployeeContractTemplateResponse);
    }

    @RequestMapping(value = "/get-employee-contract-templates/{clientId}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeTimesheet(@PathVariable BigInteger clientId)
    {
        SmartBankingResponse smartBankingResponse = employmentService.getEmployeeContractTemplateByClientId(clientId);
        return ResponseEntity.ok().body(smartBankingResponse);
    }

    @RequestMapping(value = "/create-employee-group", method = RequestMethod.POST)
    public ResponseEntity createEmployeeGroup(@RequestBody CreateEmployeeGroupRequest createEmployeeGroup)
    {
        SmartBankingResponse smartBankingResponse = employmentService.createEmployeeGroup(createEmployeeGroup);
        return ResponseEntity.ok().body(smartBankingResponse);
    }

    @RequestMapping(value = "/get-employee-groups/{clientId}", method = RequestMethod.GET)
    public ResponseEntity createEmployeeGroup(@PathVariable BigInteger clientId)
    {
        SmartBankingResponse smartBankingResponse = employmentService.getEmployeeGroupByClientId(clientId);
        return ResponseEntity.ok().body(smartBankingResponse);
    }


}
