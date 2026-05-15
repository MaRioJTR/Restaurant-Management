package com.restaurant.patterns.factory;

import com.restaurant.models.DessertItem;
import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Concrete factory for dessert menu products.
 *
 * Role: Creates DessertItem instances without exposing product construction to clients.
 * Pattern: GoF Factory Method concrete creator.
 */
public class DessertFactory implements MenuItemFactory {
    @Override
    public MenuItem create(String name, String description, String preparationNotes, BigDecimal price) {
        return new DessertItem(name, description, preparationNotes, price);
    }
}
