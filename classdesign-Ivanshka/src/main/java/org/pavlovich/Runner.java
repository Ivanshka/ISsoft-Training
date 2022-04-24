package org.pavlovich;

import org.pavlovich.domain.Order;
import org.pavlovich.domain.OrderAddress;
import org.pavlovich.domain.OrderItem;
import org.pavlovich.service.OrderService;
import org.pavlovich.storage.OrderStorage;
import org.pavlovich.validation.OrderItemValidator;
import org.pavlovich.validation.OrderValidator;

import java.util.Random;
import java.util.UUID;

public class Runner {
    public static void main(String[] args) {
        OrderAddress address = new OrderAddress("Minsk", "someStreet", "bigHouse", 1);
        Order order = new Order(UUID.randomUUID(), address);
        OrderItem firstItem = new OrderItem("Laptop HP ProBook ", 1, 1899);
        OrderItem secondItem = new OrderItem("Smartphone Xiaomi Redmi Note 4X ", 1, 299);

        OrderService service = new OrderService(new OrderStorage(), new OrderValidator(), new OrderItemValidator());
        service.addItemToOrder(order, firstItem);
        service.addItemToOrder(order, secondItem);
        service.placeOrder(order);

        int sum = service.calculateSummaryPrice(order);
        System.out.println("Order sum is " + sum);
    }
}
