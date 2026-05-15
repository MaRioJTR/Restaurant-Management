package com.restaurant.patterns.factory;

import com.restaurant.models.DrinkItem;
import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Concrete factory for drink menu products.
 *
 * Role: Creates DrinkItem instances without exposing product construction to clients.
 * Pattern: GoF Factory Method concrete creator.
 */
public class DrinkFactory implements MenuItemFactory {
    @Override
    public MenuItem create(String name, String description, String preparationNotes, BigDecimal price) {
        return new DrinkItem(name, description, preparationNotes, price);
    }
}
