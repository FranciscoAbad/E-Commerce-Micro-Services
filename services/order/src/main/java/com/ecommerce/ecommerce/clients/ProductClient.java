package com.ecommerce.ecommerce.clients;


import com.ecommerce.ecommerce.models.ProductPurchaseRequest;
import com.ecommerce.ecommerce.models.ProductPurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "product-client",
url = "${application.config.product-url}")
public interface ProductClient {
    @PostMapping("/purchase")
    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requestBody);
}
