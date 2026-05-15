package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds sauce customization to a menu item.
 *
 * Role: Concrete decorator that changes price, description, and preparation notes.
 * Pattern: GoF Decorator.
 */
public class ExtraSauceDecorator extends MenuItemDecorator {
    private static final BigDecimal EXTRA_COST = new BigDecimal("0.75");

    public ExtraSauceDecorator(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", extra sauce";
    }

    @Override
    public String getPreparationNotes() {
        return wrappedItem.getPreparationNotes() + " Serve with extra sauce on the side.";
    }

    @Override
    public BigDecimal getPrice() {
        return wrappedItem.getPrice().add(EXTRA_COST);
    }
}
