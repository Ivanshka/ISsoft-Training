package org.pavlovich.validation;

import org.pavlovich.domain.Order;
import org.pavlovich.exception.InvalidOrderException;

public class OrderValidator {
    public void validateOrder(Order order) {
        if (order == null)
            throw new InvalidOrderException("Order is null.");

        if(order.getItems().isEmpty())
            throw new InvalidOrderException("Item list is required.");

        if(order.getAddress() == null)
            throw new InvalidOrderException("Address is null, but required.");

        if(order.getUserId() == null)
            throw new InvalidOrderException("User ID is null, but required and can't be equal 0.");
    }
}
