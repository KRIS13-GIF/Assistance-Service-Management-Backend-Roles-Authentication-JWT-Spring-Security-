package com.kris.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

/*

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service
    ) {
        return args -> {
            var acceptance = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ACCEPTANCE)
                    .build();
            System.out.println("Admin token: " + service.register(acceptance).getAccessToken());

            var technician = RegisterRequest.builder()
                    .firstname("Manager")
                    .lastname("Manager")
                    .email("manager@mail.com")
                    .password("password")
                    .role(TECHNICIAN)
                    .build();
            System.out.println("Manager token: " + service.register(technician).getAccessToken());

            var user = RegisterRequest.builder()
                    .firstname("User")
                    .lastname("User")
                    .email("user@gmail.com")
                    .password("password")
                    .role(USER)
                    .build();
            System.out.println("User token: " + service.register(user).getAccessToken());


        };
    }*/

}
