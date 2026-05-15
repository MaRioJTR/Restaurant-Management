package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds cheese customization to a menu item.
 *
 * Role: Dynamically augments description and price.
 * Pattern: GoF Decorator concrete decorator.
 */
public class ExtraCheese extends MenuItemDecorator {
    private static final BigDecimal EXTRA_COST = new BigDecimal("1.50");

    public ExtraCheese(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", extra cheese";
    }

    @Override
    public BigDecimal getPrice() {
        return wrappedItem.getPrice().add(EXTRA_COST);
    }
}
