package org.pavlovich.service;

import org.junit.Before;
import org.junit.Test;
import org.pavlovich.domain.Order;
import org.pavlovich.exception.InvalidOrderException;
import org.pavlovich.samples.OrderSamples;
import org.pavlovich.storage.OrderStorage;
import org.pavlovich.validation.OrderItemValidator;
import org.pavlovich.validation.OrderValidator;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.pavlovich.samples.OrderSamples.*;

public class OrderServiceTest {
    private OrderService orderService;

    @Before
    public void before(){
        orderService = new OrderService(new OrderStorage(), new OrderValidator(), new OrderItemValidator());
    }

    @Test
    public void addItemToOrder(){
        Order order = getOrderWithoutItem();
        orderService.addItemToOrder(order, getValidOrderItem());
    }

    @Test
    public void placeValidOrder(){
        checkNotNull(orderService.placeOrder(getOrderWithoutItem()));
    }

    @Test
    public void placeInvalidOrder(){
        try{
            Order order = OrderSamples.getInvalidOrder();
            assertNull(orderService.placeOrder(order));
        }catch (InvalidOrderException ignored){ }
    }

    @Test
    public void calculateSummaryPrice(){
        int sum = orderService.calculateSummaryPrice(getValidOrderWithItem());
        final int testOrderSummaryPrice = 1000;
        assertEquals(sum, testOrderSummaryPrice);
    }
}
