package com.kris.security.controllers;

import com.kris.security.models.FileName;
import com.kris.security.models.ProductResponse;
import com.kris.security.services.AcceptanceServices;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/acceptance")
public class AcceptanceControllers {

    private final AcceptanceServices services;

    public AcceptanceControllers(AcceptanceServices services) {
        this.services = services;
    }

    @GetMapping
    public String get(){
        return "GET m";
    }

    @PostMapping("/product/{id}")
    public ProductResponse acceptProduct(@PathVariable int id,
                                         @RequestBody FileName fileName) throws Exception {

        return services.acceptProduct(id, fileName);
    }




}
