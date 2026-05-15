package com.restaurant.patterns.factory;

import com.restaurant.models.FoodItem;
import com.restaurant.models.MenuItem;

import java.math.BigDecimal;

/**
 * Concrete creator for food products.
 *
 * Role: Encapsulates FoodItem construction.
 * Pattern: Factory Method concrete creator.
 */
public class FoodItemCreator extends MenuItemCreator {
    @Override
    public MenuItem createMenuItem(String name, String description, BigDecimal price) {
        return new FoodItem(name, description, price);
    }
}
