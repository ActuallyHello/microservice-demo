package com.happyfxmas.orderservice.service;

import com.happyfxmas.orderservice.domain.Order;
import com.happyfxmas.orderservice.domain.OrderLineItems;
import com.happyfxmas.orderservice.dto.InventoryResponse;
import com.happyfxmas.orderservice.dto.OrderLineItemsDTO;
import com.happyfxmas.orderservice.dto.OrderRequest;
import com.happyfxmas.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final WebClient webClient;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList().stream()
                .map(this::mapToDTO)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri("http://localhost:8082/api/inventories",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(Objects.requireNonNull(inventoryResponseArray))
                .allMatch(InventoryResponse::isInStock);
        if (allProductsInStock) {
            orderRepo.save(order);
        } else {
            throw new IllegalArgumentException("Products are not in stock, please try again later!");
        }
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        return orderLineItems;
    }
}
