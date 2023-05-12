package com.avinash.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class InventoryResponseDTO {
    private String skuCode;
    private Boolean isInStock;
}
