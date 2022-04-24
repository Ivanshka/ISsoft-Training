package org.pavlovich.storage;

import org.junit.Before;
import org.junit.Test;
import org.pavlovich.domain.Order;
import org.pavlovich.samples.OrderSamples;

import java.util.UUID;

import static org.junit.Assert.*;

public class OrderStorageTest {
    private OrderStorage storage;

    @Before
    public void before(){
        storage = new OrderStorage();
    }

    @Test
    public void persist(){
        Order order = OrderSamples.getValidOrderWithItem();
        assertNotNull(storage.persist(order));
    }

    @Test
    public void findByUserId(){
        Order order = OrderSamples.getInvalidOrder();
        UUID id = storage.persist(order);
        Order testOrder = storage.findById(id);
        assertNotNull(testOrder);
        assertEquals(order, testOrder);
    }
}
