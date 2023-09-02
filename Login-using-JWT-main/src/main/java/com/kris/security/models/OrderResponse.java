package com.kris.security.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

}
