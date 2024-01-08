package com.happyfxmas.inventoryservice.service;

import com.happyfxmas.inventoryservice.dto.InventoryResponse;
import com.happyfxmas.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public boolean isInStock(String skuCode) {
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }

    public List<InventoryResponse> isListInStock(List<String> skuCode) {
        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .toList();
    }
}
