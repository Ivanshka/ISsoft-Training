package org.pavlovich.validation;

import org.pavlovich.domain.OrderItem;
import org.pavlovich.exception.InvalidOrderException;

public class OrderItemValidator {
    public void validateOrderItem(OrderItem item) throws InvalidOrderException {
        if (item == null)
            throw new InvalidOrderException("OrderItem is null.");

        if(item.getName() == null)
            throw new InvalidOrderException("Item name is null, but required.");

        if(item.getCount() < 1)
            throw new InvalidOrderException("Item count should be positive.");

        if(item.getCost() == 0)
            throw new InvalidOrderException("Item cost can't be equal 0.");
    }
}
