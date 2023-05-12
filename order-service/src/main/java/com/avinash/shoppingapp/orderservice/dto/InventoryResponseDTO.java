package com.avinash.shoppingapp.orderservice.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {
    private String skuCode;
    private Boolean isInStock;
}
