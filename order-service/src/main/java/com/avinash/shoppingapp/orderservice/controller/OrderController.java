package com.avinash.shoppingapp.orderservice.controller;


import com.avinash.shoppingapp.orderservice.dto.OrderRequest;
import com.avinash.shoppingapp.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethodForInventoryService")
//    @TimeLimiter(name = "inventory")
//    @Retry(name="inventory")
//    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
//        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
//    }
//
//    public CompletableFuture<String> fallbackMethodForInventoryService(OrderRequest orderRequest, RuntimeException runtimeException){
//        return CompletableFuture.supplyAsync(()-> "Order failed");
//    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethodForInventoryService")
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        return orderService.placeOrder(orderRequest);
    }

    public String fallbackMethodForInventoryService(OrderRequest orderRequest, RuntimeException runtimeException){
        return "Order failed";
    }
}
