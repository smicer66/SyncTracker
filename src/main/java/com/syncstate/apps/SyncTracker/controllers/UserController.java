package com.syncstate.apps.SyncTracker.controllers;



import com.syncstate.apps.SyncTracker.models.requests.CreateUserRequest;
import com.syncstate.apps.SyncTracker.models.requests.LoginRequest;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private com.syncstate.apps.SyncTracker.service.AuthService authService;

    @RequestMapping(value = "/create-new-user", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody CreateUserRequest createUserRequest)
    {
        CreateUserResponse createUserResponse = userService.createNewUser(createUserRequest);
        return ResponseEntity.ok().body(createUserResponse);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity doLogin(@RequestBody LoginRequest loginRequest)
    {
        ResponseEntity loginResponse = authService.doLogin(loginRequest);
        return loginResponse;
//        return ResponseEntity.ok("Hello");
    }
}
