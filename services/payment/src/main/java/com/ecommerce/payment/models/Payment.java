package com.ecommerce.payment.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="payment")
public class Payment {
    @Id
    @GeneratedValue
    private Integer id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Integer orderId;
    @CreatedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(updatable = false,nullable = false)
    private LocalDateTime lastModifiedDate;

}
