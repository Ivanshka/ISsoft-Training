package org.pavlovich.domain;

import java.util.UUID;

public class OrderItem {
    private String name;
    private int count;
    private int cost;
    private UUID id;
    private UUID orderId;

    public OrderItem(String name, int count, int cost) {
        this.name = name;
        this.count = count;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }
}
