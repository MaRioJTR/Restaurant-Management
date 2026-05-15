package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Base decorator for menu items.
 *
 * Role: Wraps another MenuItem and delegates shared behavior.
 * Pattern: GoF Decorator abstract decorator. It preserves the MenuItem contract
 * so services can treat base and decorated items uniformly.
 */
public abstract class MenuItemDecorator implements MenuItem {
    protected final MenuItem wrappedItem;

    protected MenuItemDecorator(MenuItem wrappedItem) {
        this.wrappedItem = Objects.requireNonNull(wrappedItem, "wrappedItem");
    }

    @Override
    public String getName() {
        return wrappedItem.getName();
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription();
    }

    @Override
    public String getPreparationNotes() {
        return wrappedItem.getPreparationNotes();
    }

    @Override
    public BigDecimal getPrice() {
        return wrappedItem.getPrice();
    }
}
