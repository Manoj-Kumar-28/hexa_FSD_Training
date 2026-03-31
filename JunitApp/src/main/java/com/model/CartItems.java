package com.model;

import java.math.BigDecimal;

public class CartItems {
    private int id;
    private String name;
    private BigDecimal price;
    private int quantity;

    public CartItems(int id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
