package com.restaurant.patterns.factory;

import com.restaurant.models.FoodItem;
import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Concrete factory for food menu products.
 *
 * Role: Creates FoodItem instances without exposing product construction to clients.
 * Pattern: GoF Factory Method concrete creator.
 */
public class FoodFactory implements MenuItemFactory {
    @Override
    public MenuItem create(String name, String description, String preparationNotes, BigDecimal price) {
        return new FoodItem(name, description, preparationNotes, price);
    }
}
