package com.kris.security.controllers;

import com.kris.security.models.ProductRequest;
import com.kris.security.models.ProductResponse;
import com.kris.security.models.UpdateRequestProduct;
import com.kris.security.services.CustomerServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final CustomerServices services;

    public UserController(CustomerServices services) {
        this.services = services;
    }


    @PostMapping("/create/{id}")
    public ProductResponse createProduct(@PathVariable int id, @RequestBody ProductRequest productRequest) throws Exception {
        return services.createProduct(id,productRequest);
    }

    @PutMapping("/consult/{id}")
    public ResponseEntity<ProductResponse> modifyProduct(
            @PathVariable String id,
            @RequestBody UpdateRequestProduct updateRequestProduct
    ) throws Exception {
        ProductResponse productResponse = services.consult(Integer.valueOf(id), updateRequestProduct);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }




}
