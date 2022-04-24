package org.pavlovich.storage;

import org.junit.Before;
import org.junit.Test;
import org.pavlovich.samples.OrderSamples;

import java.util.HashMap;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class OrderItemStorageTest {
    private OrderItemStorage storage;

    @Before
    public void before(){
        storage = new OrderItemStorage(new HashMap<>());
    }

    @Test
    public void persist(){
        checkNotNull(storage.persist(OrderSamples.getValidOrderItem()));
    }

    @Test
    public void findByOrderId(){
        UUID orderItemId = storage.persist(OrderSamples.getValidOrderItem());
        checkNotNull(storage.findByOrderId(orderItemId));
    }
}
