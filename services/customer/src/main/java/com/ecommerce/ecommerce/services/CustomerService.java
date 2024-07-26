package com.ecommerce.ecommerce.services;


import com.ctc.wstx.util.StringUtil;
import com.ecommerce.ecommerce.exceptions.CustomerNotFoundException;
import com.ecommerce.ecommerce.mappers.CustomerMapper;
import com.ecommerce.ecommerce.models.Customer;
import com.ecommerce.ecommerce.models.CustomerRequest;
import com.ecommerce.ecommerce.models.CustomerResponse;
import com.ecommerce.ecommerce.repositories.CustomerRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Autowired
    public CustomerService(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Integer createCustomer(CustomerRequest request) {
        return repository.save(mapper.toCustomer(request)).getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer= repository.findById(request.id()).orElseThrow(()-> new CustomerNotFoundException("Couldn't find customer with ID:: "+ request.id()));
        mergeCustomer(customer,request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address()!=null){
            customer.setAddress(request.address());
        }
    }


    public List<CustomerResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existById(Integer customerId) {
        return repository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(Integer customerId) {
        return repository.findById(customerId).map(mapper::fromCustomer).orElseThrow(()-> new CustomerNotFoundException("Customer not found"));
    }

    public void delete(Integer customerId) {
        var customer= repository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Could not delete, customer not found"));
        repository.delete(customer);
    }
}
