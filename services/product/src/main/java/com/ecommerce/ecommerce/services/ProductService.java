package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dtos.ProductPurchaseRequest;
import com.ecommerce.ecommerce.dtos.ProductPurchaseResponse;
import com.ecommerce.ecommerce.dtos.ProductRequest;
import com.ecommerce.ecommerce.dtos.ProductResponse;
import com.ecommerce.ecommerce.exceptions.ProductPurchaseException;
import com.ecommerce.ecommerce.mappers.ProductMapper;
import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    @Autowired
    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Integer createProduct(ProductRequest request) {
        Product product= mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds= request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts= repository.findAllByIdInOrderById(productIds);
        if(productIds.size()!=storedProducts.size()){
            throw new ProductPurchaseException("One or more products does not exist");
        }

        var sortedRequest= request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        var purchaseProducts= new ArrayList<ProductPurchaseResponse>();

        for(int i=0;i<sortedRequest.size();i++){
            var product= storedProducts.get(i);
            var productRequest=sortedRequest.get(i);
            if(product.getAvailableQuantity()< productRequest.quantity()){
                throw new ProductPurchaseException("Insufficent strock quantity for product with ID:: "+productRequest.productId());
            }
            var newAvailableQuantity=product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchaseProducts.add(mapper.toProductPurchaseResponse(product,productRequest.quantity()));
        }
        return null;
    }


    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with the ID::"));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
