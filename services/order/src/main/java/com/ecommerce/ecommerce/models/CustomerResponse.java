package com.ecommerce.ecommerce.models;

public record CustomerResponse(
        Integer id,
        String firstName,
        String lastName,
        String email
) {
}
