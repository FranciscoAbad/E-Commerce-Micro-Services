package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.models.CustomerRequest;
import com.ecommerce.ecommerce.models.CustomerResponse;
import com.ecommerce.ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(name="/api/v1/customer")
public class CustomerController {
    private final CustomerService service;


    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

@PostMapping
    public ResponseEntity<Integer> createCustomer(
            @RequestBody @Valid CustomerRequest request
){
    return ResponseEntity.ok(service.createCustomer(request));
}

@PutMapping
    public ResponseEntity<Void> updateCustomer(
            @RequestBody @Valid CustomerRequest request
){
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
}

@GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
}

@GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existById(
            @PathVariable("customer-id") Integer customerId
){
        return ResponseEntity.ok(service.existById(customerId));
}

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            @PathVariable("customer-id") Integer customerId
    ){
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
            public ResponseEntity<Void> delete(
                    @PathVariable("customer-id") Integer customerId
    ){
    service.delete(customerId);
    return ResponseEntity.accepted().build();
}

}
