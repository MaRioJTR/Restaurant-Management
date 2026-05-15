package com.restaurant.patterns.factory;

import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Abstract creator for menu item products.
 *
 * Role: Declares the factory method that subclasses implement.
 * Pattern: GoF Factory Method. Client code depends on this abstraction and lets
 * concrete creators choose which MenuItem implementation to instantiate.
 */
public abstract class MenuItemCreator {
    public abstract MenuItem createMenuItem(String name, String description, BigDecimal price);
}
