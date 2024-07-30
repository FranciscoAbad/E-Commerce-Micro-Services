package com.ecommerce.ecommerce.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message="Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer should be precised")
        @Positive(message = "Customer id should be positive")
        Integer customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<ProductPurchaseRequest> products

) {
}
