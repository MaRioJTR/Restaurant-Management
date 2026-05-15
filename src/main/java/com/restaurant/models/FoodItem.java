package com.restaurant.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Concrete food product in the menu domain.
 *
 * Role: Immutable domain entity for food menu items.
 * Pattern: Factory Method concrete product. FoodItem is created by FoodItemCreator
 * so callers do not depend directly on this constructor.
 */
public class FoodItem implements MenuItem {
    private final String name;
    private final String description;
    private final String preparationNotes;
    private final BigDecimal price;

    public FoodItem(String name, String description, BigDecimal price) {
        this(name, description, "Prepare hot and plate before service.", price);
    }

    public FoodItem(String name, String description, String preparationNotes, BigDecimal price) {
        this.name = Objects.requireNonNull(name, "name");
        this.description = Objects.requireNonNull(description, "description");
        this.preparationNotes = Objects.requireNonNull(preparationNotes, "preparationNotes");
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
    public String getPreparationNotes() {
        return preparationNotes;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}
