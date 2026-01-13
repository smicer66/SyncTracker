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



}
