package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds sauce customization to a menu item.
 *
 * Role: Dynamically augments description and price.
 * Pattern: GoF Decorator concrete decorator.
 */
public class ExtraSauce extends MenuItemDecorator {
    private static final BigDecimal EXTRA_COST = new BigDecimal("0.75");

    public ExtraSauce(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", extra sauce";
    }

    @Override
    public BigDecimal getPrice() {
        return wrappedItem.getPrice().add(EXTRA_COST);
    }
}
