package com.ecommerce.ecommerce.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
