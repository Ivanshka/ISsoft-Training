package com.pavlovich.domain.wagon;

import com.google.common.base.Preconditions;

public class Freight {
    private final int number;
    private final int weight;
    private final int price;

    public Freight(int number, int weight, int price) {
        Preconditions.checkArgument(weight > 0, "Weight can't be less than 0.");
        Preconditions.checkArgument(price > 0, "Price can't be less than 0.");

        this.number = number;
        this.weight = weight;
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public int getWeight() {
        return weight;
    }

    public int getPrice() {
        return price;
    }
}
