package com.restaurant.repositories;

import com.restaurant.models.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {
    void save(Order order);

    Optional<Order> findById(UUID orderId);
}
