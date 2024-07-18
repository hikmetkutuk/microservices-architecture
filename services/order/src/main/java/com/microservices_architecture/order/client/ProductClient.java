package com.microservices_architecture.order.client;

import com.microservices_architecture.order.dto.PurchaseRequest;
import com.microservices_architecture.order.dto.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)
public interface ProductClient {

    @PostMapping("/purchase")
    Optional<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests);
}
