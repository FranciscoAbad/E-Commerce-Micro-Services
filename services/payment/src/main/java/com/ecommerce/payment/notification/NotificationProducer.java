package com.ecommerce.payment.notification;


import com.ecommerce.payment.dtos.PaymentNotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationProducer {

    private final KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate;


    @Autowired
    public NotificationProducer(KafkaTemplate<String, PaymentNotificationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(PaymentNotificationRequest request){
        log.info("Sending notification with body <{}>",request);
        Message<PaymentNotificationRequest> message= MessageBuilder
                .withPayload(request)
                .setHeader(KafkaHeaders.TOPIC,"payment-topic")
                .build();

        kafkaTemplate.send(message);
    }

}
