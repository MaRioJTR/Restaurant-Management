package com.restaurant.services;

import com.restaurant.models.MenuItem;
import com.restaurant.patterns.decorator.ExtraCheese;
import com.restaurant.patterns.decorator.ExtraSauce;
import com.restaurant.patterns.decorator.NoOnions;
import com.restaurant.patterns.factory.DrinkItemCreator;
import com.restaurant.patterns.factory.FoodItemCreator;
import com.restaurant.patterns.factory.MenuItemCreator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service facade for menu catalog creation.
 *
 * Role: Keeps JavaFX controllers from directly constructing menu products.
 * Pattern: Delegates item creation to Factory Method creators, preserving the
 * existing factory architecture while giving the UI a thin service boundary.
 */
public class MenuService {
    private final MenuItemCreator foodCreator = new FoodItemCreator();
    private final MenuItemCreator drinkCreator = new DrinkItemCreator();

    public List<MenuItem> getFoodItems() {
        return List.of(
                foodCreator.createMenuItem("House Burger", "Beef burger with lettuce, onions, and tomato", new BigDecimal("12.00")),
                foodCreator.createMenuItem("Grilled Chicken", "Herb-marinated chicken with seasonal vegetables", new BigDecimal("14.50")),
                foodCreator.createMenuItem("Chocolate Cake", "Dark chocolate slice with cream", new BigDecimal("6.25"))
        );
    }

    public List<MenuItem> getDrinkItems() {
        return List.of(
                drinkCreator.createMenuItem("Iced Tea", "Fresh brewed black tea over ice", new BigDecimal("3.25")),
                drinkCreator.createMenuItem("Sparkling Water", "Chilled mineral water", new BigDecimal("2.75"))
        );
    }

    public MenuItem customize(MenuItem item, boolean extraCheese, boolean noOnions, boolean extraSauce) {
        MenuItem customized = item;
        if (extraCheese) {
            customized = new ExtraCheese(customized);
        }
        if (noOnions) {
            customized = new NoOnions(customized);
        }
        if (extraSauce) {
            customized = new ExtraSauce(customized);
        }
        return customized;
    }
}
