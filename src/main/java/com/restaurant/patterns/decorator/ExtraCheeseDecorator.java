package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds cheese customization to a menu item.
 *
 * Role: Concrete decorator that changes price, description, and preparation notes.
 * Pattern: GoF Decorator; it wraps the same MenuItem interface at runtime.
 */
public class ExtraCheeseDecorator extends MenuItemDecorator {
    private static final BigDecimal EXTRA_COST = new BigDecimal("1.50");

    public ExtraCheeseDecorator(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", extra cheese";
    }

    @Override
    public String getPreparationNotes() {
        return wrappedItem.getPreparationNotes() + " Add melted cheese before final plating.";
    }

    @Override
    public BigDecimal getPrice() {
        return wrappedItem.getPrice().add(EXTRA_COST);
    }
}
