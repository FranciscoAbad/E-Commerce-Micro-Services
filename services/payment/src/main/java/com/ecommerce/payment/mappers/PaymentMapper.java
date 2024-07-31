package com.ecommerce.payment.mappers;


import com.ecommerce.payment.dtos.PaymentRequest;
import com.ecommerce.payment.models.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {


    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .orderId(request.orderId())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .build();
    }
}
