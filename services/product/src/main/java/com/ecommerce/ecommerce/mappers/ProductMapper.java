package com.ecommerce.ecommerce.mappers;


import com.ecommerce.ecommerce.dtos.ProductPurchaseRequest;
import com.ecommerce.ecommerce.dtos.ProductPurchaseResponse;
import com.ecommerce.ecommerce.dtos.ProductRequest;
import com.ecommerce.ecommerce.dtos.ProductResponse;
import com.ecommerce.ecommerce.models.Category;
import com.ecommerce.ecommerce.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(ProductRequest request){
         return Product.builder()
                 .id(request.id())
                 .name(request.name())
                 .description(request.description())
                 .price(request.price())
                 .availableQuantity(request.availableQuantity())
                 .category(Category.builder().id(request.categoryId()).build())
                 .build();
    }

    public ProductResponse toProductResponse(Product product){
        return new ProductResponse(
                product.getId(), product.getName(),product.getDescription(), product.getAvailableQuantity(),product.getPrice(),product.getCategory().getId(),product.getCategory().getName(), product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity){
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
