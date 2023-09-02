package com.kris.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.kris.security.user.Permission.*;
import static com.kris.security.user.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final  AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/**")
                        .permitAll()


                        //for the user
                        .requestMatchers("/api/v1/user/**").hasRole(USER.name())
                        .requestMatchers(POST,"/api/v1/user/create/**").hasAnyAuthority(USER_CREATE_PRODUCT.name())
                        .requestMatchers(PUT,"/api/v1/user/consult/**").hasAnyAuthority(USER_CONSULT.name())

                        // for the acceptance
                        .requestMatchers("/api/v1/acceptance/**").hasRole(ACCEPTANCE.name())
                        .requestMatchers(POST,"/api/v1/acceptance/product/**").hasAnyAuthority(ACCEPTANCE_CREATE.name())

                        // for the technician
                        .requestMatchers("/api/v1/technician/**").hasRole(TECHNICIAN.name())
                        .requestMatchers(POST,"/api/v1/technician/createOrder/**").hasAnyAuthority(TECHNICIAN_CREATE_ORDER.name())
                        .requestMatchers(PUT,"/api/v1/technician/repair/**").hasAnyAuthority(TECHNICIAN_REPAIR.name())
                        .requestMatchers(PUT,"/api/v1/technician/no-repair/**").hasAnyAuthority(TECHNICIAN_NOT_REPAIR.name())
                        .requestMatchers(POST,"/api/v1/technician/finish/**").hasAnyAuthority(TECHNICIAN_PUT_FINISH.name())

                        .anyRequest().authenticated()

                )
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)

        ;


        return http.build();


    }


}
