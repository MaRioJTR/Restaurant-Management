package com.restaurant.services;

import com.restaurant.models.MenuItem;
import com.restaurant.patterns.decorator.ExtraCheese;
import com.restaurant.patterns.decorator.ExtraSauce;
import com.restaurant.patterns.decorator.NoOnions;
import com.restaurant.patterns.factory.DessertFactory;
import com.restaurant.patterns.factory.DrinkFactory;
import com.restaurant.patterns.factory.FoodFactory;
import com.restaurant.patterns.factory.MenuItemFactory;

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
    private final MenuItemFactory foodFactory = new FoodFactory();
    private final MenuItemFactory drinkFactory = new DrinkFactory();
    private final MenuItemFactory dessertFactory = new DessertFactory();

    public List<MenuItem> getFoodItems() {
        return List.of(
                foodFactory.create("House Burger", "Beef burger with lettuce, onions, and tomato", "Grill patty medium and toast bun.", new BigDecimal("12.00")),
                foodFactory.create("Grilled Chicken", "Herb-marinated chicken with seasonal vegetables", "Grill chicken fully and rest before serving.", new BigDecimal("14.50"))
        );
    }

    public List<MenuItem> getDessertItems() {
        return List.of(
                dessertFactory.create("Chocolate Cake", "Dark chocolate slice with cream", "Plate cold with cream garnish.", new BigDecimal("6.25"))
        );
    }

    public List<MenuItem> getDrinkItems() {
        return List.of(
                drinkFactory.create("Iced Tea", "Fresh brewed black tea over ice", "Serve over fresh ice.", new BigDecimal("3.25")),
                drinkFactory.create("Sparkling Water", "Chilled mineral water", "Serve chilled with glass.", new BigDecimal("2.75"))
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
