package com.ecommerce.ecommerce.mappers;

import com.ecommerce.ecommerce.models.Address;
import com.ecommerce.ecommerce.models.Customer;
import com.ecommerce.ecommerce.models.CustomerRequest;
import com.ecommerce.ecommerce.models.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request){

        if(request==null){
            return null;
        }

        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
