package com.kris.security.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/management")
public class Management {


    @GetMapping
    public String get(){
        return "GET m";
    }

    @PostMapping
    public String post(){
        return "POST m";
    }


}
