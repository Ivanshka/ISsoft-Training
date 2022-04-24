package org.pavlovich.storage;

import org.pavlovich.domain.Order;

import java.util.*;

public class OrderStorage {
    private final Map<UUID, Order> orderStorage = new HashMap<>();

    public UUID persist(Order order) {
        UUID id = UUID.randomUUID();
        order.setId(id);
        orderStorage.put(id, order);
        return id;
    }

    public Order findById(UUID id) {
        return orderStorage.get(id);
    }

    public List<Order> findByUserId(UUID userId) {
        List<Order> byUserId = new LinkedList<>();
        for (Order order : orderStorage.values()) {
            if (order.getUserId() == userId)
                byUserId.add(order);
        }

        return byUserId;
    }

    public Order loadFromDbByUserId(UUID userId){
        throw new UnsupportedOperationException("Operation is not implemented");
    }
}
