package com.avinash.shoppingapp.orderservice.service;

import com.avinash.shoppingapp.orderservice.dto.InventoryResponseDTO;
import com.avinash.shoppingapp.orderservice.dto.OrderList;
import com.avinash.shoppingapp.orderservice.dto.OrderRequest;
import com.avinash.shoppingapp.orderservice.event.OrderPlacedEvent;
import com.avinash.shoppingapp.orderservice.model.Order;
import com.avinash.shoppingapp.orderservice.model.OrderItems;
import com.avinash.shoppingapp.orderservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private final WebClient.Builder webClientBuilder;

    public String  placeOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderItemsList(orderRequest
                        .getOrderListList()
                        .stream()
                        .map(this::orderItemToOrderList)
                        .toList())
                .build();
        List<String> skuCodes = order.getOrderItemsList().stream().map(OrderItems::getSkuCode).toList();
        InventoryResponseDTO[] result = webClientBuilder.build().get()
                .uri("http://InventoryClient/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block();

        Boolean finalResult = Arrays.stream(result).allMatch(InventoryResponseDTO::getIsInStock);
        if (finalResult){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        }else {
            throw new IllegalArgumentException("Product out of stock");
        }
    }

    private OrderItems orderItemToOrderList(OrderList orderList){
        return OrderItems.builder()
                .skuCode(orderList.getSkuCode())
                .price(orderList.getPrice())
                .quantity(orderList.getQuantity())
                .build();
    }
}
