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

//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
////        this "authenticationManager" will do all the job for us, in case the username (email in this case) or the password is not correct, an exception will be thrown.
////        authenticationManager.authenticate(...) will use the "AuthenticationManager" to perform the authentication. the "UsernamePasswordAuthenticationToken "is created with the provided email and password. the "AuthenticationManager" then attempts to authenticate the user with the configured authentication providers. in our case, the "DaoAuthenticationProvider" configured in ApplicationConfig will handle this.
////      - the above "AuthenticationManager" will check if the token "UsernamePasswordAuthenticationToken" of the user
////        is valid/authenticate or not. i am not sure though if it also store the Authentication object of the user after a
////        successful authentication in the SecurityContextHolder.

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + request.getEmail())
                );

        var jwtAccessToken = jwtService.generateAccessToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .build();
    }

    //    TODO: refresh token endpoint + having automatic rotation
}
