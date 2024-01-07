package com.happyfxmas.inventoryservice.service;

import com.happyfxmas.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public boolean isInStock(String skuCode) {
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }
}
