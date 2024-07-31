package com.ecommerce.payment.dtos;

import com.ecommerce.payment.models.Customer;
import com.ecommerce.payment.models.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
