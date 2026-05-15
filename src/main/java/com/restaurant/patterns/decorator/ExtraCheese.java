package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds cheese customization to a menu item.
 *
 * Role: Dynamically augments description and price.
 * Pattern: GoF Decorator concrete decorator.
 */
public class ExtraCheese extends ExtraCheeseDecorator {
    public ExtraCheese(MenuItem wrappedItem) {
        super(wrappedItem);
    }
}
