package org.pavlovich.samples;

import org.pavlovich.domain.Order;
import org.pavlovich.domain.OrderAddress;
import org.pavlovich.domain.OrderItem;
import org.pavlovich.domain.OrderStatus;

import java.util.UUID;

public class OrderSamples{
    private static final OrderAddress address =
            new OrderAddress("Minsk", "someStreet", "bigHouse", 1);

    public static Order getValidOrderWithItem(){
        Order order = new Order(UUID.randomUUID(), address);
        order.getItems().add(getValidOrderItem());
        order.setStatus(OrderStatus.WAIT_OF_PROCESSING);
        return order;
    }

    public static Order getOrderWithoutItem(){
        Order order = new Order(UUID.randomUUID(), address);
        order.getItems().add(getValidOrderItem());
        order.setStatus(OrderStatus.WAIT_OF_PROCESSING);
        return order;
    }

    public static OrderItem getValidOrderItem(){
        OrderItem item = new OrderItem("Item1", 1, 1000);
        item.setId(UUID.randomUUID());
        return item;
    }

    public static Order getInvalidOrder() {
        return new Order(UUID.randomUUID(), null);
    }
}
