package com.kris.security.services;

import com.kris.security.entities.Product;
import com.kris.security.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServices {

    private final CustomerServices customerServices;
    private final ProductRepository productRepository;
    public ProductServices(CustomerServices customerServices, ProductRepository productRepository) {
        this.customerServices = customerServices;
        this.productRepository = productRepository;
    }

    public Product findProduct(String id){
        return productRepository.findById(Integer.valueOf(id)).get();
    }





}
