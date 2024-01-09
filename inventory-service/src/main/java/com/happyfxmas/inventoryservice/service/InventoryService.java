package com.happyfxmas.inventoryservice.service;

import com.happyfxmas.inventoryservice.dto.InventoryResponse;
import com.happyfxmas.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public boolean isInStock(String skuCode) {
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }

    @SneakyThrows
    public List<InventoryResponse> isListInStock(List<String> skuCode) {
        log.info("WAIT START");
        Thread.sleep(10000);
        log.info("WAIT END");
        return inventoryRepo.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .isInStock(inventory.getQuantity() > 0)
                        .build())
                .toList();
    }
}
