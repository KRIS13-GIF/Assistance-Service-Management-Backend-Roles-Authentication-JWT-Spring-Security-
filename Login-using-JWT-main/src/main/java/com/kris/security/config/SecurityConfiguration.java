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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.kris.security.user.Permission.*;
import static com.kris.security.user.Role.ADMIN;
import static com.kris.security.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final  AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth.requestMatchers("/api/v1/auth/**")
                        .permitAll()

                        // for the management
                        .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                        .requestMatchers(GET,"/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                        .requestMatchers(POST,"/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())


                        // for the admin
                        .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                        .requestMatchers(GET,"/api/v1/admin/**").hasAnyAuthority(ADMIN_READ.name())
                        .requestMatchers(POST,"/api/v1/admin/**").hasAnyAuthority(ADMIN_CREATE.name())



                        .anyRequest().authenticated()

                )
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)

        ;


        return http.build();


    }


}
