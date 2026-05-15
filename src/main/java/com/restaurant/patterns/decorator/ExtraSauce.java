package com.restaurant.patterns.decorator;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Adds sauce customization to a menu item.
 *
 * Role: Dynamically augments description and price.
 * Pattern: GoF Decorator concrete decorator.
 */
public class ExtraSauce extends ExtraSauceDecorator {
    public ExtraSauce(MenuItem wrappedItem) {
        super(wrappedItem);
    }
}
