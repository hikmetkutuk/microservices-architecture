package com.microservices_architecture.product.mapper;

import com.microservices_architecture.product.dto.ProductPurchaseResponse;
import com.microservices_architecture.product.dto.ProductRequest;
import com.microservices_architecture.product.dto.ProductResponse;
import com.microservices_architecture.product.model.Category;
import com.microservices_architecture.product.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest productRequest) {
        if (productRequest == null) {
            return null;
        }
        return Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .quantity(productRequest.quantity())
                .price(productRequest.price())
                .category(
                        Category.builder()
                                .id(productRequest.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponse fromProduct(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
