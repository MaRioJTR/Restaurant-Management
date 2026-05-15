package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

/**
 * Removes onions from a menu item.
 *
 * Role: Dynamically augments description without changing price.
 * Pattern: GoF Decorator concrete decorator.
 */
public class NoOnions extends MenuItemDecorator {
    public NoOnions(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", no onions";
    }
}
