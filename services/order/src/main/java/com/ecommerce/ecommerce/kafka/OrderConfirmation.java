package com.ecommerce.ecommerce.kafka;

import com.ecommerce.ecommerce.models.CustomerResponse;
import com.ecommerce.ecommerce.models.PaymentMethod;
import com.ecommerce.ecommerce.models.ProductPurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerInformation,
        List<ProductPurchaseResponse> products
) {
}
