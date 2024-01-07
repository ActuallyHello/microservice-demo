package com.happyfxmas.orderservice.repo;

import com.happyfxmas.orderservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
