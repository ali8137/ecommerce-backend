package com.ali.ecommerce.auth.config;

import com.ali.ecommerce.auth.config.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

//    @Autowired
//    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
//        this.jwtService = jwtService;
//        this.userDetailsService = userDetailsService;
//    }

//    (9) implement the doFilterInternal method of the JwtAuthenticationFilter class:
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader;
        final String jwtAccessToken;
        final String userEmail;

        authHeader = request.getHeader("Authorization");
        log.info("authHeader: {}", authHeader);

        // - if the Authorization header is missing or doesn't start
        //   with "Bearer ", the request continues down the filter chain as unauthenticated
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Missing or invalid Authorization header");

            filterChain.doFilter(request, response);
            return;
        }

        jwtAccessToken = authHeader.substring(7);

        log.info("jwtAccessToken: {}", jwtAccessToken);

        userEmail = jwtService.extractUsername(jwtAccessToken);

//      - create the Authentication object for the user (this Authentication object enables us
//        to track the user information when sending a request, after it gets authenticated to
//        access the relative endpoints), and add it to the security context:
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//          - SecurityContextHolder.getContext() will return the current security context of the user.
//            SecurityContextHolder.getContext().getAuthentication() will return the current
//            Authentication object of the user only if the user was authenticated (permitted to access
//            the relative endpoints).
//          - the above means apply the code below only if the user didn't
//            already get his Authentication object

//            - load the user details (in the database) to use them against the claims of the access token:
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(userEmail);

//            - check if the access token is valid (credentials in the token are the same as the ones in the database):
//            - the checking will include verification of the sign key, and then checking of the username and the expiration date
            if (jwtService.isAccessTokenValid(jwtAccessToken, userDetails)) {

                //    TODO: add the case of automatic triggering (in the frontend side) of
                //     sending request to the refresh token endpoint, in case of the access token
                //     being present but expired. check this chat with ChatGPT to know what to do in
                //     the frontend side at this level https://chatgpt.com/share/674b59e0-b488-8013-99cb-bf04f2cc7ef4

                // add the user to the security context:

//              - UsernamePasswordAuthenticationToken is the default implementation of
//                Authentication interface.
//              - the Authentication token includes the userDetails of the user and
//                the authorities of the user (derived from the userDetails of the user)
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }

        filterChain.doFilter(request, response);
    }
}
