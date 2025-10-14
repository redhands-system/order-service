package com.redhands.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private Long partId;      // 주문할 부품 ID
    private int quantity;     // 주문 수량
}