package com.avinash.shoppingapp.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderList {
    private Long id;
    private String skuCode;
    private Integer quantity;
    private BigDecimal price;
}
