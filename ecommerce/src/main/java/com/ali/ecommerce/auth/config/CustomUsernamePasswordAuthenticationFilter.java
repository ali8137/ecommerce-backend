//package com.ali.ecommerce.auth.config;
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import java.io.IOException;
//import java.util.Map;
//
//@Slf4j
////@RequiredArgsConstructor
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//
//    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
////        setFilterProcessesUrl("/api/auth/login"); // Set the endpoint this filter will handle
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//        try {
////            // Map JSON body to a LoginRequest object
////            LoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
////            log.info("Email: {}, Password: {}", loginRequest.getEmail(), loginRequest.getPassword());
//
//            Map<String, String> credentials = new ObjectMapper().
//                    readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
//
//            log.info("credentials: {}", credentials);
//
//            String email = credentials.get("email");
//            String password = credentials.get("password");
//
//            UsernamePasswordAuthenticationToken authRequest =
//                    new UsernamePasswordAuthenticationToken(email, password);
//            log.info("authRequest: {}", authRequest);
//
//            setDetails(request, authRequest);
//
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials())
//            );
//        } catch (IOException e) {
//            throw new RuntimeException("Invalid login request format", e);
//        }
//    }
//
////    @Override
////    protected void successfulAuthentication(
////            HttpServletRequest request,
////            HttpServletResponse response,
////            FilterChain chain,
////            Authentication authResult) throws IOException {
////
//////        // Generate JWT (replace with your JWT generation logic)
//////        String token = "dummy-jwt-token"; // Replace with actual token generation
//////
//////        // Add token to the response
//////        Map<String, String> responseBody = new HashMap<>();
//////        responseBody.put("token", token);
//////        response.setContentType("application/json");
//////        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
////
////        log.info("Authentication successful");
////
////    }
////
////    @Override
////    protected void unsuccessfulAuthentication(
////            HttpServletRequest request,
////            HttpServletResponse response,
////            AuthenticationException failed) throws IOException {
//////        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//////        response.getWriter().write("Invalid credentials");
////        log.info("Authentication failed");
////    }
//
//    // DTO for login request
//    @Setter
//    @Getter
//    static class LoginRequest {
//        // Getters and setters
//        private String email;
//        private String password;
//
//    }
//}














//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//@Slf4j
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//
//    @Autowired
//    public CustomUsernamePasswordAuthenticationFilter(
//            //  - version 1 and 3 of replacing the UsernamePasswordAuthenticationFilter
//            //    with the CustomUsernamePasswordAuthenticationFilter:
//            AuthenticationManager authenticationManager
////            the above is method injection of AuthenticationManager bean
//    ) {
//        //  - version 1 and 3 of replacing the UsernamePasswordAuthenticationFilter
//        //    with the CustomUsernamePasswordAuthenticationFilter:
//        super(authenticationManager);
//
//        setFilterProcessesUrl("/api/auth/login");
////        the default filterProcessesUrl is /login
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            // Parse request body
//            Map<String, String> credentials = new ObjectMapper().
//                    readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
//            log.info("credentials: {}", credentials);
//
//            String email = credentials.get("email");
//            String password = credentials.get("password");
//
//            UsernamePasswordAuthenticationToken authRequest =
//                    new UsernamePasswordAuthenticationToken(email, password);
//            log.info("authRequest: {}", authRequest);
//
//            // Set details for authentication
//            this.setDetails(request, authRequest);
//
//            return this.getAuthenticationManager().authenticate(authRequest);
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to parse authentication request body", e);
//        }
//    }
//}
