package com.restaurant.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Concrete dessert product in the menu domain.
 *
 * Role: Immutable menu product for dessert items.
 * Pattern: Factory Method concrete product created by DessertFactory.
 */
public class DessertItem implements MenuItem {
    private final String name;
    private final String description;
    private final String preparationNotes;
    private final BigDecimal price;

    public DessertItem(String name, String description, BigDecimal price) {
        this(name, description, "Plate cold unless requested warm.", price);
    }

    public DessertItem(String name, String description, String preparationNotes, BigDecimal price) {
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
