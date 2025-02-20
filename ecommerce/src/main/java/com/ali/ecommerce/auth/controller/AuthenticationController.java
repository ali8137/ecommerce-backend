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
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // create the register endpoint:
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        // add the user to the database after encrypting the password
        // generate and return the jwt token
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    // create the login endpoint:
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }

//    TODO: refresh token endpoint + having automatic rotation
}
