package com.syncstate.apps.SyncTracker.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.probase.potzr.SmartBanking.models.enums.IdentificationDocumentType;
import com.probase.potzr.SmartBanking.models.enums.TokenType;
import com.probase.potzr.SmartBanking.models.enums.UserStatus;
import com.syncstate.apps.SyncTracker.models.Token;
import com.syncstate.apps.SyncTracker.models.User;
import com.syncstate.apps.SyncTracker.models.requests.CreateUserRequest;
import com.syncstate.apps.SyncTracker.models.requests.SendInvitationRequest;
import com.syncstate.apps.SyncTracker.models.responses.CreateUserResponse;
import com.syncstate.apps.SyncTracker.models.responses.SmartBankingResponse;
import com.syncstate.apps.SyncTracker.repositories.ITokenRepository;
import com.syncstate.apps.SyncTracker.repositories.IUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITokenRepository tokenRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @Value("${user.account.token.valid.period}")
    private int userAccountTokenValidPeriod;

    @Value("${path.uploads.user.profile.picture}")
    private String fileDestinationPath;




    public Map createNewUser(CreateUserRequest createUserRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(createUserRequest.getPassword());

        User user = new User();
        user.setUsername(createUserRequest.getMobileNumber());
        user.setEmailAddress(createUserRequest.getEmailAddress());
        user.setMobileNumber(createUserRequest.getMobileNumber());
        user.setPassword(encodedPassword);
        user.setUserStatus(UserStatus.INACTIVE);
        userRepository.saveAndFlush(user);


        Token token  = new Token();
        token.setTokenOwnedByEntityId(user.getUserId());
        token.setToken(RandomStringUtils.randomNumeric(6));
        token.setData(RandomStringUtils.randomAlphanumeric(64).toLowerCase());
        token.setExpiredAt(LocalDateTime.now().plusHours(userAccountTokenValidPeriod));
        token.setTokenType(TokenType.SIGNUP);
        tokenRepository.save(token);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return map;
    }


    public User getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        return user;
    }


    public void sendInvitationRequest(List<SendInvitationRequest> sendInvitationRequestList) {
        ObjectMapper objectMapper = new ObjectMapper();
        sendInvitationRequestList.stream().map(s -> {

            try {
                Token token = new Token();
                token.setExpiredAt(LocalDateTime.now().plusDays(7));
                token.setData(objectMapper.writeValueAsString(s));
                token.setTokenType(TokenType.EMPLOYEE_SIGNUP_INVITATION);
                token.setTokenOwnedByEntityId(s.getEmployeeId());
                token.setToken(RandomStringUtils.randomAlphanumeric(64).toLowerCase());
                token = (Token)tokenRepository.save(token);
                return token;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
