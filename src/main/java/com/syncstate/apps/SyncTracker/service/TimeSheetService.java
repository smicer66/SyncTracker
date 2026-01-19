package com.syncstate.apps.SyncTracker.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.*;
import com.syncstate.apps.SyncTracker.models.enums.TimesheetActivityType;
import com.syncstate.apps.SyncTracker.models.requests.ApplyTimesheetAdjustmentRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateScheduledWorkShiftRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateTimesheetClockInRequest;
import com.syncstate.apps.SyncTracker.models.requests.CreateTimesheetClockOutRequest;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.models.responses.GetEmployeeTimesheetResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.models.responses.TimesheetClockInResponse;
import com.syncstate.apps.SyncTracker.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.syncstate.apps.SyncTracker.models.dto.EmployeeTimeSheetDTO;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TimeSheetService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IScheduledWorkRepository iScheduledWorkRepository;

    @Autowired
    private ITimesheetRepository iTimesheetRepository;

    @Autowired
    private ITimesheetNoteRepository iTimesheetNoteRepository;

    @Autowired
    private IEmployeeRepository iEmployeeRepository;

    @Autowired
    private IEmployeeBioDataRepository iEmployeeBioDataRepository;

    @Autowired
    private ITimesheetAdjustmentRepository iTimesheetAdjustmentRepository;

    @Autowired
    private IClientRepository iClientRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Value("${kafka.email.create.topic}")
    private String createKafkaSMSTopic;

    @Autowired
    private KafkaTemplate<Long, SyncTrackerEmail> kafkaTemplate;


    public GetEmployeeTimesheetResponse getEmployeeTimesheet(BigInteger employeeId, BigInteger clientId)
    {
        Collection<Timesheet> timesheetList = this.iTimesheetRepository.getTimesheetByEmployeeId(employeeId, clientId);
        Employee employee = this.iEmployeeRepository.getEmployeeByEmployeeId(employeeId);
        EmployeeBioData employeeBioData = this.iEmployeeBioDataRepository.getEmployeeBioDetailsByEmployeeId(employeeId, clientId);

        Map<String, EmployeeTimeSheetDTO> employeeTimeSheetDTOList = new HashMap<String, EmployeeTimeSheetDTO>();
        EmployeeTimeSheetDTO employeeTimeSheetDTO = new EmployeeTimeSheetDTO();
        employeeTimeSheetDTO.setEmployeeName(employeeBioData.getLastName().toUpperCase() + ", " + employeeBioData.getFirstName());
        employeeTimeSheetDTO.setEmployeeNumber(employee.getEmployeeNumber());
        employeeTimeSheetDTO.setEmployeeSocialSecurityNo(employee.getEmployeeSocialSecurityNo());
        employeeTimeSheetDTO.setEmployeeNumber(employee.getEmployeeNumber());

        List timesheetListMapped = timesheetList.stream().map(t -> {
            EmployeeTimeSheetDTO.TimeSheetDTO timeSheetDTO = new EmployeeTimeSheetDTO.TimeSheetDTO();
            timeSheetDTO.setClockOutTime(t.getClockOutTime());
            timeSheetDTO.setClockInTime(t.getClockInTime());
            timeSheetDTO.setApproximatedWorkHoursInMinutes(ChronoUnit.MINUTES.between(t.getClockInTime(), t.getClockOutTime()));
            return timeSheetDTO;
        }).toList();

        employeeTimeSheetDTO.setTimeSheetList(timesheetListMapped);

        employeeTimeSheetDTOList.put("employeeTimesheet", employeeTimeSheetDTO);


        GetEmployeeTimesheetResponse getEmployeeTimesheetResponse = new GetEmployeeTimesheetResponse();
        getEmployeeTimesheetResponse.setStatusCode(0);
        getEmployeeTimesheetResponse.setMessage("Timesheet of the employee has been summarized.");
        getEmployeeTimesheetResponse.setResponseObject(employeeTimeSheetDTOList);
        return getEmployeeTimesheetResponse                                                                                                                                                                ;
    }


    public TimesheetClockInResponse clockIn(CreateTimesheetClockInRequest createTimesheetClockInRequest) {
        String noteContents = createTimesheetClockInRequest.getNoteContent();
        BigInteger clientId = createTimesheetClockInRequest.getClientId();
        String jwtToken = this.request.getHeader("Authorization").substring("Bearer ".length());
        try {
            User user = tokenService.getUserFromToken(request);


            if(user!=null) {
                Employee employee = iEmployeeRepository.getEmployeeByUserIdAndClientId(user.getUserId(), clientId);
                if(employee!=null)
                {
                    LocalDateTime currentTime = LocalDateTime.now();
                    ScheduledWork scheduledWork = iScheduledWorkRepository.getScheduledWorkByEmployeeIdByTime(employee.getEmployeeId(), currentTime);

                    Timesheet timesheet = new Timesheet();
                    timesheet.setClockInTime(currentTime);
                    timesheet.setClientId(clientId);
                    timesheet.setEmployeeId(employee.getEmployeeId());
                    timesheet.setScheduledWorkId(scheduledWork.getScheduledWorkId());
                    timesheet = (Timesheet) iTimesheetRepository.save(timesheet);

                    if(noteContents!=null)
                    {
                        TimesheetNote timesheetNote = new TimesheetNote();
                        timesheetNote.setNoteContent(noteContents);;
                        timesheetNote.setTimesheetId(timesheet.getTimesheetId());
                        timesheetNote.setNoteCreatedByEmployeeId(employee.getEmployeeId());
                        timesheetNote.setTimesheetActivityType(TimesheetActivityType.CLOCKIN);
                        iTimesheetNoteRepository.save(timesheetNote);
                    }

                    TimesheetClockInResponse timesheetClockInResponse = new TimesheetClockInResponse();
                    timesheetClockInResponse.setStatusCode(0);
                    timesheetClockInResponse.setMessage("You have been clocked in. Do not forget to clock out at the end of your work shift.");
                    return timesheetClockInResponse;
                }
                else {
                    throw new SyncTrackerException("You could not be clocked in. We were unable to link your profile to the current company you are clocking in from. Please ensure you have selected the company before trying to clock in.");
                }

            }
            else {
                throw new SyncTrackerException("Your session has timed out. We are logging you out so you can log in again to clock-in.");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }

    }





    public TimesheetClockInResponse clockOut(CreateTimesheetClockOutRequest createTimesheetClockOutRequest) {
        String noteContents = createTimesheetClockOutRequest.getNoteContent();
        BigInteger clientId = createTimesheetClockOutRequest.getClientId();
        BigInteger timeSheetId = createTimesheetClockOutRequest.getTimeSheetId();
        String jwtToken = this.request.getHeader("Authorization").substring("Bearer ".length());
        try {
            User user = tokenService.getUserFromToken(request);


            if(user!=null) {
                Employee employee = iEmployeeRepository.getEmployeeByUserIdAndClientId(user.getUserId(), clientId);
                if(employee!=null)
                {
                    LocalDateTime currentTime = LocalDateTime.now();
                    Timesheet timesheet = iTimesheetRepository.getById(timeSheetId);
                    timesheet.setClockOutTime(currentTime);
                    iTimesheetRepository.save(timesheet);

                    if(noteContents!=null)
                    {
                        TimesheetNote timesheetNote = new TimesheetNote();
                        timesheetNote.setNoteContent(noteContents);;
                        timesheetNote.setTimesheetId(timesheet.getTimesheetId());
                        timesheetNote.setNoteCreatedByEmployeeId(employee.getEmployeeId());
                        timesheetNote.setTimesheetActivityType(TimesheetActivityType.CLOCKOUT);
                        iTimesheetNoteRepository.save(timesheetNote);
                    }

                    TimesheetClockInResponse timesheetClockInResponse = new TimesheetClockInResponse();
                    timesheetClockInResponse.setStatusCode(0);
                    timesheetClockInResponse.setMessage("You have been clocked out.");
                    return timesheetClockInResponse;
                }
                else {
                    throw new SyncTrackerException("You could not be clocked out. We were unable to link your profile to the current company you are clocking out from. Please ensure you have selected the company before trying to clock out.");
                }

            }
            else {
                throw new SyncTrackerException("Your session has timed out. We are logging you out so you can log in again to clock-out.");
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }

    }


    private Map responseObject;
    public SmartBankingResponse applyTimesheetAdjustment(ApplyTimesheetAdjustmentRequest applyTimesheetAdjustmentRequest) {
        TimesheetAdjustment timesheetAdjustment = new TimesheetAdjustment();
        BeanUtils.copyProperties(applyTimesheetAdjustmentRequest, timesheetAdjustment);
        timesheetAdjustment = (TimesheetAdjustment) iTimesheetAdjustmentRepository.save(timesheetAdjustment);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setMessage("An adjustment has been made to the employees timesheet entry.");
        return smartBankingResponse;
    }


    public SmartBankingResponse createScheduledWorkShift(String clientCode, SyncTrackerEmailKafkaService syncTrackerEmailKafkaService,
         List<CreateScheduledWorkShiftRequest> createScheduledWorkShiftRequestList) {

        Client client = this.iClientRepository.getClientByClientCode(clientCode);
        Map<BigInteger, List<String>> employeeList = new HashMap<BigInteger, List<String>>();
        List<ScheduledWork> steList = new ArrayList<>();
        steList  = createScheduledWorkShiftRequestList.stream().map(cs -> {
            ScheduledWork scheduledWork = new ScheduledWork();
            scheduledWork.setCreatedByEmployeeId(cs.getCreatedByEmployeeId());
            scheduledWork.setExpectedEndTime(cs.getExpectedEndTime());
            scheduledWork.setExpectedStartTime(cs.getExpectedStartTime());
            scheduledWork.setEmployeeId(cs.getEmployeeId());
            scheduledWork.setExpectedBreakPeriodInMins(cs.getExpectedBreakPeriodInMins());
            scheduledWork = (ScheduledWork) iScheduledWorkRepository.save(scheduledWork);

            return scheduledWork;
        }).collect(Collectors.toList());



        Map<BigInteger, List<ScheduledWork>> scheduledWorkGroupings = steList.stream().collect(groupingBy(ScheduledWork::getEmployeeId));

        scheduledWorkGroupings.keySet().stream().map(swg -> {
            List<ScheduledWork> sw = scheduledWorkGroupings.get(swg);
            String message = sw.stream().map(s->{
                return s.getExpectedStartTime().format(dtf) + "|||" + s.getExpectedStartTime().format(dtf);
            }).collect(Collectors.joining());
            SyncTrackerEmail syncTrackerEmail = new SyncTrackerEmail();
            syncTrackerEmail.setEmailSubject("Your new shifts - " + client.getClientName());
            syncTrackerEmail.setEmailMessage(message);
            EmployeeBioData employeeBioData = this.iEmployeeBioDataRepository.getEmployeeBioDetailsByEmployeeId(sw.get(0).getEmployeeId(), client.getClientId());
            syncTrackerEmail.setEmailRecipient(employeeBioData.getEmailAddress());

            this.kafkaTemplate.send(createKafkaSMSTopic, syncTrackerEmail);

            return null;
        });


        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setMessage("An adjustment has been made to the employees timesheet entry.");
        return smartBankingResponse;
    }
}
