package com.ali.ecommerce.auth.service;

import com.ali.ecommerce.auth.DTO.AuthenticationRequest;
import com.ali.ecommerce.auth.DTO.AuthenticationResponse;
import com.ali.ecommerce.auth.DTO.RegisterRequest;
import com.ali.ecommerce.auth.config.service.JwtService;
import com.ali.ecommerce.exception.UserException;
import com.ali.ecommerce.model.Role;
import com.ali.ecommerce.model.User;
import com.ali.ecommerce.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        // check if the user already exists in the database:
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserException("Email already exists, please login instead.");
        }

        // add the user to the database after encrypting the password
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                /* TODO: there might be a need to change this after having
                     ADMIN role in the application
                     */
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
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found with username: " + request.getEmail()
                        )
                );

        var jwtAccessToken = jwtService.generateAccessToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .build();
    }

    //    TODO: refresh token endpoint + having automatic rotation


    public Boolean isTokenExpired(String token) {
        try {
            return jwtService.isAccessTokenExpired(token);
        } catch (ExpiredJwtException e) {
            return true; // If the token is expired, return true instead of throwing an error
        }
        // extractExpiration method in class JwtService throws an ExpiredJwtException when token is expired
    }
}
