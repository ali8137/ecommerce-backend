package com.ali.ecommerce.auth.service;

import com.ali.ecommerce.auth.DTO.AuthenticationRequest;
import com.ali.ecommerce.auth.DTO.AuthenticationResponse;
import com.ali.ecommerce.auth.DTO.RegisterRequest;
import com.ali.ecommerce.auth.config.service.JwtService;
import com.ali.ecommerce.model.Role;
import com.ali.ecommerce.model.User;
import com.ali.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

//    @Autowired
//    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtService = jwtService;
//    }

    public AuthenticationResponse register(RegisterRequest request) {

        // add the user to the database after encrypting the password
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
//                TODO: change this after having ADMIN role in the application
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // generate and return the jwt token to the frontend
        var jwtAccessToken = jwtService.generateAccessToken(user);

        return AuthenticationResponse.builder()
                        .accessToken(jwtAccessToken)
                        .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + request.getEmail())
                );

        var jwtAccessToken = jwtService.generateAccessToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .build();
    }
}
