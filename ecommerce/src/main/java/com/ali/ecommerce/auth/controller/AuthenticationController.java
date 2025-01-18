package com.ali.ecommerce.auth.controller;

import com.ali.ecommerce.auth.DTO.AuthenticationRequest;
import com.ali.ecommerce.auth.DTO.AuthenticationResponse;
import com.ali.ecommerce.auth.DTO.RegisterRequest;
import com.ali.ecommerce.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

//    public AuthenticationController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }

//    (6) create the register endpoint:
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
//            (7) create RegisterRequest, the AuthenticationResponse and the AuthenticationRequest class:
            @RequestBody RegisterRequest request
            ) {
//        #1 add the user to the database after encrypting the password
//        #2 generate and return the jwt token
//        both are service functionalities => delegate the work to the service layer

//        (8) create the AuthenticationService class and implement the register() method:
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
//      - in both cases, the "register" and the "login", a JWT token will be generated and
//        returned. in case of registration, after registering, the user will be able to access
//        the application resources using the token, like the case of login
    }

//    (6) create the login endpoint:
//    @PostMapping("/login")
    @PostMapping("/authenticate")
//    the requests to the endpoint "/login" are intercepted by
//    the filter "UsernamePasswordAuthenticationFilter" of "spring security" library. which
//    is dedicated for the form login with username and password inputs. don't use UsernamePasswordAuthenticationFilter
//    filter here to validate your data
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
//        #1 validate the user credentials: in this case, the filter
//        "UsernamePasswordAuthenticationFilter" will take care of this
//      - related to the above note: don't use UsernamePasswordAuthenticationFilter filter
//        here to validate your data. using AuthenticationManager is enough
//        #2 generate and return the jwt token

        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }


//    TODO: refresh token endpoint + having automatic rotation
}
