package org.pavlovich.service;

import org.pavlovich.domain.Order;
import org.pavlovich.domain.OrderItem;
import org.pavlovich.storage.OrderItemStorage;
import org.pavlovich.validation.OrderItemValidator;

import java.util.List;
import java.util.UUID;

public class OrderItemService {
    private final OrderItemStorage itemStorage;
    private final OrderItemValidator itemValidator;

    public OrderItemService(OrderItemStorage itemStorage, OrderItemValidator itemValidator) {
        this.itemStorage = itemStorage;
        this.itemValidator = itemValidator;
    }

    public UUID placeOrderItem(OrderItem item) {
        itemValidator.validateOrderItem(item);

        return itemStorage.persist(item);
    }

    public List<OrderItem> loadAllByOrderId(UUID orderId) throws Exception {
        List<OrderItem> byOrderId = itemStorage.findByOrderId(orderId);
        if (byOrderId.isEmpty())
            throw new Exception("Order items with specify order ID not found.");
        return byOrderId;
    }
}
