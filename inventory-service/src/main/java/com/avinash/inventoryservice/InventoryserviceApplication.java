package com.avinash.inventoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryserviceApplication.class, args); }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//        return args -> {
//            inventoryRepository.save(Inventory.builder().quantity(12).skuCode("1237").build());
//            inventoryRepository.save(Inventory.builder().quantity(10).skuCode("1238").build());
//            inventoryRepository.save(Inventory.builder().quantity(13).skuCode("1239").build());
//        };
//    }
}
