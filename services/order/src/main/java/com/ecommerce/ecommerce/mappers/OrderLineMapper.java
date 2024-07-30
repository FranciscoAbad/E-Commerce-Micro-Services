package com.ecommerce.ecommerce.mappers;


import com.ecommerce.ecommerce.models.Order;
import com.ecommerce.ecommerce.models.OrderLine;
import com.ecommerce.ecommerce.models.OrderLineRequest;
import com.ecommerce.ecommerce.models.OrderLineResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .productId(request.productId())
                .quantity(request.quantity())
                .order(Order.builder()
                        .id(request.orderId())
                        .build())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
