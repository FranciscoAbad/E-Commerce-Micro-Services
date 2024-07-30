package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.clients.CustomerClient;
import com.ecommerce.ecommerce.clients.ProductClient;
import com.ecommerce.ecommerce.exceptions.BusinessException;
import com.ecommerce.ecommerce.kafka.OrderConfirmation;
import com.ecommerce.ecommerce.kafka.OrderProducer;
import com.ecommerce.ecommerce.mappers.OrderMapper;
import com.ecommerce.ecommerce.models.*;
import com.ecommerce.ecommerce.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

@Autowired
    public OrderService(CustomerClient customerClient, ProductClient productClient, OrderRepository orderRepository, OrderMapper mapper, OrderLineService orderLineService, OrderProducer orderProducer) {
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
    }

    public Integer createOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(()-> new BusinessException("Cannot create order:: No customer exists with ID::" + request.customerId()));

        var purchasedProducts =productClient.purchaseProducts(request.products());

        var order=orderRepository.save(mapper.toOrder(request));

        for(ProductPurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId).map(mapper::fromOrder).orElseThrow(()-> new EntityNotFoundException("No order found with the ID:: " + orderId));
    }
}
