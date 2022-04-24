package org.pavlovich.storage;

import org.pavlovich.domain.Order;
import org.pavlovich.domain.OrderItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderItemStorage {
    private final Map<UUID, OrderItem> orderItems;

    public OrderItemStorage(Map<UUID, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public UUID persist(OrderItem item){
        throw new UnsupportedOperationException("Operation is not implemented");
    }

    public List<OrderItem> findByOrderId(UUID orderId) {
        throw new UnsupportedOperationException("Operation is not implemented");
    }

    public OrderItem loadFromDbById(UUID id){
        throw new UnsupportedOperationException("Operation is not implemented");
    }
}
