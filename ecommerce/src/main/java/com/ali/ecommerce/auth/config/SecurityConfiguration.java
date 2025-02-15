package com.ali.ecommerce.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- beginning
//    - the below is wrong because the AuthenticationManager of
//      UserNamePasswordAuthenticationFilter must be set directly, and
//      because UsernamePasswordAuthenticationFilter is not a bean
////  - version 2 of replacing the UsernamePasswordAuthenticationFilter
////    with the CustomUsernamePasswordAuthenticationFilter:
//    private final CustomUsernamePasswordAuthenticationFilter customFilter;
//    private final AuthenticationManager authenticationManager;
//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- end


//    (1) create the SecurityFilterChain bean:
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http

//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- beginning
//            //  - version 1 of replacing the UsernamePasswordAuthenticationFilter
//            //    with the CustomUsernamePasswordAuthenticationFilter:
//            CustomUsernamePasswordAuthenticationFilter customFilter
//            //    the above is method injection of the CustomUsernamePasswordAuthenticationFilter bean

////            //  - version 3 of replacing the UsernamePasswordAuthenticationFilter
////            //    with the CustomUsernamePasswordAuthenticationFilter:
//            AuthenticationManager authenticationManager
//////            the above is the AuthenticationManager bean
//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- end
    ) throws Exception {

//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- beginning
////        //  - version 3 of replacing the UsernamePasswordAuthenticationFilter
////        //    with the CustomUsernamePasswordAuthenticationFilter:
//        CustomUsernamePasswordAuthenticationFilter customFilter =
//                new CustomUsernamePasswordAuthenticationFilter(authenticationManager);
//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- end

        http
                .csrf(CsrfConfigurer::disable)
                //  the csrf above is disabled because we are using JWT authentication
                // .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                // - Using SecurityConfig for CORS (with Spring Security)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/auth/**").permitAll()
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/register").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                (3) create the AuthenticationProvider bean in another @Configuration
//                class, "ApplicationConfig" class in this case:
                .authenticationProvider(authenticationProvider)
//                (4) create the JwtAuthenticationFilter bean without implementing its
//                doFilterInternal method:
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- beginning
//                .addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class);
////                .addFilter(customFilter);

//                //  - version 1 of replacing the UsernamePasswordAuthenticationFilter
//                //    with the CustomUsernamePasswordAuthenticationFilter:
//                //      - the above line will replace the UsernamePasswordAuthenticationFilter with
//                //        the CustomUsernamePasswordAuthenticationFilter
//                .addFilterAt(customFilter, UsernamePasswordAuthenticationFilter.class);
//    ignore the below as UsernamePasswordAuthenticationFilter is dedicated to form login with username and password inputs ---- end

//    - the below is wrong because the AuthenticationManager of
//      UserNamePasswordAuthenticationFilter must be set directly, and
//      because UsernamePasswordAuthenticationFilter is not a bean
//                //  - version 2 of replacing the UsernamePasswordAuthenticationFilter
//                //    with the CustomUsernamePasswordAuthenticationFilter:
//                .addFilterAt(getCustomFilter(), UsernamePasswordAuthenticationFilter.class);
//                //      - the above line will replace the UsernamePasswordAuthenticationFilter with
//                //        the CustomUsernamePasswordAuthenticationFilter

//        TODO: OAuth2 Configuration


        return http.build();
    }


//    (2) create the CorsConfigurationSource bean:
    // - Using SecurityConfig for CORS (with Spring Security)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("Authorization"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


//    - the below is wrong because the AuthenticationManager of
//      UserNamePasswordAuthenticationFilter must be set directly, and
//      because UsernamePasswordAuthenticationFilter is not a bean
//    //  - version 2 of replacing the UsernamePasswordAuthenticationFilter
//    //    with the CustomUsernamePasswordAuthenticationFilter:
//    public CustomUsernamePasswordAuthenticationFilter getCustomFilter() {
//        customFilter.setAuthenticationManager(authenticationManager);
//        return customFilter;
//    }

}