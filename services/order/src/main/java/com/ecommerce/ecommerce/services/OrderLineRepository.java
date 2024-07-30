package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.models.OrderLine;
import com.ecommerce.ecommerce.models.OrderLineResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
