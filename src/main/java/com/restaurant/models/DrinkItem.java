package com.restaurant.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Concrete drink product in the menu domain.
 *
 * Role: Immutable domain entity for beverage menu items.
 * Pattern: Factory Method concrete product. DrinkItem is produced by
 * DrinkItemCreator, preserving loose coupling between clients and products.
 */
public class DrinkItem implements MenuItem {
    private final String name;
    private final String description;
    private final BigDecimal price;

    public DrinkItem(String name, String description, BigDecimal price) {
        this.name = Objects.requireNonNull(name, "name");
        this.description = Objects.requireNonNull(description, "description");
        this.price = Objects.requireNonNull(price, "price");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
