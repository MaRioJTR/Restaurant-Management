package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

/**
 * Removes onions from a menu item.
 *
 * Role: Concrete decorator that changes description and preparation notes.
 * Pattern: GoF Decorator.
 */
public class NoOnionDecorator extends MenuItemDecorator {
    public NoOnionDecorator(MenuItem wrappedItem) {
        super(wrappedItem);
    }

    @Override
    public String getDescription() {
        return wrappedItem.getDescription() + ", no onions";
    }

    @Override
    public String getPreparationNotes() {
        return wrappedItem.getPreparationNotes() + " Remove onions from preparation.";
    }
}
