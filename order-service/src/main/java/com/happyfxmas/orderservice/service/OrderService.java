package com.happyfxmas.orderservice.service;

import com.happyfxmas.orderservice.domain.Order;
import com.happyfxmas.orderservice.domain.OrderLineItems;
import com.happyfxmas.orderservice.dto.OrderLineItemsDTO;
import com.happyfxmas.orderservice.dto.OrderRequest;
import com.happyfxmas.orderservice.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Transactional
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList().stream()
                .map(this::mapToDTO)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        orderRepo.save(order);
    }

    private OrderLineItems mapToDTO(OrderLineItemsDTO orderLineItemsDTO) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDTO.getSkuCode());
        return orderLineItems;
    }
}
