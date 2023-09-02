package com.kris.security.controllers;

import com.kris.security.entities.Ordering;
import com.kris.security.models.FinishRequest;
import com.kris.security.models.FinishResponse;
import com.kris.security.models.OrderResponse;
import com.kris.security.services.TechnicianServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/technician")
public class TechnicianControllers {

    private final TechnicianServices services;

    public TechnicianControllers(TechnicianServices services) {
        this.services = services;
    }


    @GetMapping
    public String get(){
        return "GET";
    }

    @PostMapping("/createOrder/{id1}/{id2}")
    public ResponseEntity <OrderResponse>createOrder(
            @PathVariable int  id1,
            @PathVariable int id2

    ) throws Exception {
        OrderResponse response=services.createOrder(id1, id2);
        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }

    @PutMapping("/repair/{id}")
    public void repairOrder(
            @PathVariable String id
    ) throws Exception {
        services.repairOrder(id);
    }

    @PutMapping("/no-repair/{id}")
    public void notRepairing(
            @PathVariable String id
    ) throws Exception {
        services.doNotRepair(id);
    }

    @PostMapping("/finish/{id}")
    public ResponseEntity<FinishResponse> putToFinish(
            @PathVariable String id,
            @RequestBody FinishRequest finishRequest
    ) throws Exception {
        FinishResponse finishResponse = services.putToFinish(id, finishRequest);
        return new ResponseEntity<>(finishResponse, HttpStatus.OK);
    }







}
