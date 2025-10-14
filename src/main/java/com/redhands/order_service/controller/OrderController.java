package com.redhands.order_service.controller;

import com.redhands.order_service.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final WebClient.Builder webClientBuilder;

    @Value("${backend.inventory-service.url}")
    private String inventoryServiceUrl;

    public OrderController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        // inventory-service에 재고 감소 요청
        String url = inventoryServiceUrl + "/api/parts/" + orderRequest.getPartId()
                + "/decrease?quantity=" + orderRequest.getQuantity();

        try {
            webClientBuilder.build()
                    .put()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return "주문 성공: 부품 ID " + orderRequest.getPartId()
                    + ", 수량 " + orderRequest.getQuantity();
        } catch (Exception e) {
            return "주문 실패: " + e.getMessage();
        }
    }
}