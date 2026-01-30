package com.syncstate.apps.SyncTracker.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.*;
import com.syncstate.apps.SyncTracker.models.dto.EmployeeTimeSheetDTO;
import com.syncstate.apps.SyncTracker.models.enums.TimesheetActivityType;
import com.syncstate.apps.SyncTracker.models.requests.*;
import com.syncstate.apps.SyncTracker.models.responses.GetEmployeeTimesheetResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.models.responses.TimesheetClockInResponse;
import com.syncstate.apps.SyncTracker.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmploymentService {

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private IEmployeeRepository iEmployeeRepository;

    @Autowired
    private IEmployeeBioDataRepository iEmployeeBioDataRepository;

    @Autowired
    private IClientRepository iClientRepository;

    @Autowired
    private IEmployeeContractRepository iEmployeeContractRepository;

    @Autowired
    private IEmployeeGroupRepository iEmployeeGroupRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IEmployeeContractTemplateRepository iEmployeeContractTemplateRepository;

    public SmartBankingResponse createNewEmployee(CreateNewEmployeeRequest createNewEmployeeRequest) throws SyncTrackerException {
        Employee employee = new Employee();
        employee.setEmployeeNumber(createNewEmployeeRequest.getEmployeeNumber());
        employee.setEmployeeSocialSecurityNo(createNewEmployeeRequest.getEmployeeSocialSecurityNo());
        employee.setClientId(createNewEmployeeRequest.getClientId());
        employee.setCountryOfOrigin(createNewEmployeeRequest.getCountryOfOrigin());
        employee.setDateOfBirth(createNewEmployeeRequest.getDateOfBirth());
        employee = (Employee) this.iEmployeeRepository.save(employee);

        if(employee==null)
        {
            throw new SyncTrackerException("Employee was not created successfully.");
        }

        EmployeeBioData employeeBioData = new EmployeeBioData();
        employeeBioData.setGender(createNewEmployeeRequest.getGender());
        employeeBioData.setEmailAddress(createNewEmployeeRequest.getEmailAddress());
        employeeBioData.setTitle(createNewEmployeeRequest.getTitle());
        employeeBioData.setFirstName(createNewEmployeeRequest.getFirstName());
        employeeBioData.setLastName(createNewEmployeeRequest.getLastName());
        employeeBioData.setMaritalStatus(createNewEmployeeRequest.getMaritalStatus());
        employeeBioData.setMiddleName(createNewEmployeeRequest.getMiddleName());
        employeeBioData.setMobileNumber(createNewEmployeeRequest.getMobileNumber());
        employeeBioData.setEmployeeId(employee.getEmployeeId());
        this.iEmployeeBioDataRepository.save(employeeBioData);


        Map responseObject = new HashMap();
        responseObject.put("newEmployee", employee);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setMessage("The new employee has been created. Please proceed to create a new contract for the employee");
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }



    public SmartBankingResponse getEmployeeList(String clientCode) throws SyncTrackerException {

        Client client = this.iClientRepository.getClientByClientCode(clientCode);
        Collection<EmployeeBioData> employeeBioDataList = this.iEmployeeBioDataRepository.getEmployeeBioDataByClientId(client.getClientId());


        Map responseObject = new HashMap();
        responseObject.put("employeeBioDataList", employeeBioDataList);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setMessage("The list of employees");
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }


    public SmartBankingResponse createNewEmployeeContract(CreateNewEmployeeContractRequest createNewEmployeeContractRequest) {
        EmployeeContract employeeContract = new EmployeeContract();
        BeanUtils.copyProperties(createNewEmployeeContractRequest, employeeContract);

        employeeContract = (EmployeeContract) iEmployeeContractRepository.save(employeeContract);

        Map responseObject = new HashMap();
        responseObject.put("employeeContract", employeeContract);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setMessage("A new employment contract has been created for the employee. Please send the employee an invitation to " +
                "sign up on SyncTracker mobile app to manage their shifts and timesheets.");
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }


    public SmartBankingResponse createNewEmployeeContractTemplate(CreateNewEmployeeContractTemplateRequest createNewEmployeeContractTemplateRequest) {
        EmployeeContractTemplate employeeContractTemplate = new EmployeeContractTemplate();
        BeanUtils.copyProperties(createNewEmployeeContractTemplateRequest, employeeContractTemplate);

        employeeContractTemplate = (EmployeeContractTemplate) iEmployeeContractTemplateRepository.save(employeeContractTemplate);

        employeeContractTemplate.setContractCode(String.format("%04d", employeeContractTemplate.getEmployeeContractTemplateId()));
        iEmployeeContractTemplateRepository.save(employeeContractTemplate);

        Map responseObject = new HashMap();
        responseObject.put("employeeContractTemplate", employeeContractTemplate);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setMessage("A new employment contract template has been created. You can use this template to " +
                "automate the issuance of contracts to employees. The contract code is " + employeeContractTemplate.getContractCode());
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }


    public SmartBankingResponse getEmployeeContractTemplateByClientId(BigInteger clientId)
    {
        Collection<EmployeeContractTemplate> employeeContractTemplateList = this.iEmployeeContractTemplateRepository.getEmployeeContractTemplateByClientId(clientId);
        Map responseObject = new HashMap();
        responseObject.put("employeeContractTemplateList", employeeContractTemplateList);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }

    public SmartBankingResponse createEmployeeGroup(CreateEmployeeGroupRequest createEmployeeGroup) {

        EmployeeGroup employeeGroup = new EmployeeGroup();
        BeanUtils.copyProperties(createEmployeeGroup, employeeGroup);

        employeeGroup = (EmployeeGroup) this.iEmployeeGroupRepository.save(employeeGroup);

        Map responseObject = new HashMap();
        responseObject.put("employeeGroup", employeeGroup);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }

    public SmartBankingResponse getEmployeeGroupByClientId(BigInteger clientId) {

        List<EmployeeGroup> employeeGroup = (List<EmployeeGroup>) this.iEmployeeGroupRepository.getEmployeeGroupByClientId(clientId);

        Map responseObject = new HashMap();
        responseObject.put("employeeGroup", employeeGroup);

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setResponseObject(responseObject);

        return smartBankingResponse;
    }
}
