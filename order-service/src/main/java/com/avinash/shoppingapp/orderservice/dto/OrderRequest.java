package com.avinash.shoppingapp.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<OrderList> orderListList;
}
