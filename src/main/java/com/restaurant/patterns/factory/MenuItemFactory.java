package com.restaurant.patterns.factory;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Factory Method creator for category-specific menu products.
 *
 * Role: Declares the factory method used by concrete category factories.
 * Pattern: GoF Factory Method; clients depend on this abstraction.
 */
public interface MenuItemFactory {
    MenuItem create(String name, String description, String preparationNotes, BigDecimal price);
}
