package com.ecommerce.payment.repositories;

import com.ecommerce.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
