package com.syncstate.apps.SyncTracker.controllers;



import com.syncstate.apps.SyncTracker.models.requests.ApplyTimesheetAdjustmentRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateScheduledWorkShiftRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateTimesheetClockInRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateTimesheetClockOutRequest;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.models.responses.GetEmployeeTimesheetResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.models.responses.TimesheetClockInResponse;
import com.syncstate.apps.SyncTracker.service.SyncTrackerEmailKafkaService;
import com.syncstate.apps.SyncTracker.service.TimeSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/api/v1/timesheet")
public class TimesheetController {


    @Autowired
    private TimeSheetService timeSheetService;

    @Autowired
    private SyncTrackerEmailKafkaService syncTrackerEmailKafkaService;

    @RequestMapping(value = "/clock-in", method = RequestMethod.POST)
    public ResponseEntity createTimesheetClockIn(@RequestBody CreateTimesheetClockInRequest createTimesheetClockInRequest)
    {
        TimesheetClockInResponse timesheetClockInResponse = timeSheetService.clockIn(createTimesheetClockInRequest);
        return ResponseEntity.ok().body(timesheetClockInResponse);
    }

    @RequestMapping(value = "/clock-out", method = RequestMethod.POST)
    public ResponseEntity createTimesheetClockIn(@RequestBody CreateTimesheetClockOutRequest createTimesheetClockOutRequest)
    {
        TimesheetClockInResponse timesheetClockInResponse = timeSheetService.clockOut(createTimesheetClockOutRequest);
        return ResponseEntity.ok().body(timesheetClockInResponse);
    }

    @RequestMapping(value = "/reports/employee-timesheet/{employeeId}/{clientId}", method = RequestMethod.GET)
    public ResponseEntity getEmployeeTimesheet(@PathVariable BigInteger employeeId, @PathVariable BigInteger clientId)
    {
        GetEmployeeTimesheetResponse getEmployeeTimesheetResponse = timeSheetService.getEmployeeTimesheet(employeeId, clientId);
        return ResponseEntity.ok().body(getEmployeeTimesheetResponse);
    }

    @RequestMapping(value = "/apply-timesheet-adjustment", method = RequestMethod.POST)
    public ResponseEntity applyTimesheetAdjustment(@RequestBody ApplyTimesheetAdjustmentRequest applyTimesheetAdjustmentRequest)
    {

        SmartBankingResponse smartBankingResponse = timeSheetService.applyTimesheetAdjustment(applyTimesheetAdjustmentRequest);
        return ResponseEntity.ok().body(smartBankingResponse);
    }

    @RequestMapping(value = "/create-scheduled-work-shift", method = RequestMethod.POST)
    public ResponseEntity createScheduledWorkShift(@RequestBody List<CreateScheduledWorkShiftRequest> createScheduledWorkShiftRequestList)
    {

        SmartBankingResponse smartBankingResponse = timeSheetService.createScheduledWorkShift(syncTrackerEmailKafkaService, createScheduledWorkShiftRequestList);
        return ResponseEntity.ok().body(smartBankingResponse);
    }



}
