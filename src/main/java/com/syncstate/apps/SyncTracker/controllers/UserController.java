package com.syncstate.apps.SyncTracker.controllers;



import com.syncstate.apps.SyncTracker.exceptions.SyncTrackerException;
import com.syncstate.apps.SyncTracker.models.User;
import com.syncstate.apps.SyncTracker.models.requests.CreateUserRequest;
import com.syncstate.apps.SyncTracker.models.requests.LoginRequest;
import com.syncstate.apps.SyncTracker.models.requests.SendInvitationRequest;
import com.syncstate.apps.SyncTracker.models.requests.ValidateTokenRequest;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.service.TokenService;
import com.syncstate.apps.SyncTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private com.syncstate.apps.SyncTracker.service.AuthService authService;

    @RequestMapping(value = "/create-new-user", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody CreateUserRequest createUserRequest)
    {
        Map map = userService.createNewUser(createUserRequest);
        User user = (User)map.get("user");


        Map responseObject = new HashMap();

        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setResponseObject(responseObject);
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setMessage("Your profile has been created successfully. Please check your email for an activation link to activate your SyncTracker account.");

        return ResponseEntity.ok().body(smartBankingResponse);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity doLogin(@RequestBody LoginRequest loginRequest)
    {
        ResponseEntity loginResponse = authService.doLogin(loginRequest);
        return loginResponse;
//        return ResponseEntity.ok("Hello");
    }

    @RequestMapping(value = "/send-invitation-request", method = RequestMethod.POST)
    public ResponseEntity sendInvitationRequest(@RequestBody List<SendInvitationRequest> sendInvitationRequestList)
    {
        userService.sendInvitationRequest(sendInvitationRequestList);
        SmartBankingResponse smartBankingResponse = new SmartBankingResponse();
        smartBankingResponse.setStatusCode(0);
        smartBankingResponse.setMessage("The selected employees have been sent an email containing a link to " +
                "sign up to manage their shifts, clock in and timesheet.");
        return ResponseEntity.ok().body(smartBankingResponse);
    }


    @RequestMapping(value = "/validate-token", method = RequestMethod.POST)
    public ResponseEntity validateToken(@RequestBody ValidateTokenRequest validateTokenRequest)
    {
        ResponseEntity loginResponse = null;
        try {
            loginResponse = tokenService.validateToken(validateTokenRequest);
        } catch (SyncTrackerException e) {
            throw new RuntimeException(e);
        }
        return loginResponse;
//        return ResponseEntity.ok("Hello");
    }
}
