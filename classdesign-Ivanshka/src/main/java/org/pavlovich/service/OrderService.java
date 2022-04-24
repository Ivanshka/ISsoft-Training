package org.pavlovich.service;

import org.pavlovich.domain.Order;
import org.pavlovich.domain.OrderItem;
import org.pavlovich.domain.OrderStatus;
import org.pavlovich.storage.OrderStorage;
import org.pavlovich.validation.OrderItemValidator;
import org.pavlovich.validation.OrderValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private final OrderStorage orderStorage;
    private final OrderValidator orderValidator;
    private final OrderItemValidator itemValidator;

    public OrderService(OrderStorage orderStorage, OrderValidator orderValidator, OrderItemValidator itemValidator){
        this.orderStorage = orderStorage;
        this.orderValidator = orderValidator;
        this.itemValidator = itemValidator;
    }

    public UUID placeOrder(Order order) {
        orderValidator.validateOrder(order);

        order.setStatus(OrderStatus.WAIT_OF_PROCESSING);
        order.setOrderDate(LocalDate.now());

        return orderStorage.persist(order);
    }

    public List<Order> loadAllByUserId(UUID userId) throws Exception {
        List<Order> byUserId = orderStorage.findByUserId(userId);
        if (byUserId.isEmpty())
            throw new Exception("Orders with specify user ID not found.");
        return byUserId;
    }

    public void addItemToOrder(Order order, OrderItem item){
        itemValidator.validateOrderItem(item);
        order.getItems().add(item);
    }

    public int calculateSummaryPrice(Order order){
        int sum = 0;
        for (OrderItem item : order.getItems()) {
            sum += item.getCost() * item.getCount();
        }
        return sum;
    }
}
