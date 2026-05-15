package com.restaurant.patterns.factory;

import com.restaurant.models.DrinkItem;
import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Concrete creator for drink products.
 *
 * Role: Encapsulates DrinkItem construction.
 * Pattern: Factory Method concrete creator.
 */
public class DrinkItemCreator extends MenuItemCreator {
    @Override
    public MenuItem createMenuItem(String name, String description, BigDecimal price) {
        return new DrinkItem(name, description, price);
    }
}
