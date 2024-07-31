package com.ecommerce.payment.services;

import com.ecommerce.payment.dtos.PaymentNotificationRequest;
import com.ecommerce.payment.dtos.PaymentRequest;
import com.ecommerce.payment.mappers.PaymentMapper;
import com.ecommerce.payment.notification.NotificationProducer;
import com.ecommerce.payment.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;


    @Autowired
    public PaymentService(PaymentRepository repository, PaymentMapper mapper, NotificationProducer notificationProducer) {
        this.repository = repository;
        this.mapper = mapper;
        this.notificationProducer = notificationProducer;
    }

    public Integer createPayment(PaymentRequest request) {
        var payment= repository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return null;
    }
}
