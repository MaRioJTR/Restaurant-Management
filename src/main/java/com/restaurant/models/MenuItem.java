package com.restaurant.models;

import java.math.BigDecimal;

/**
 * Base abstraction for sellable menu items.
 *
 * Role: Common product contract used by factory-created products and decorators.
 * Pattern: Factory Method product interface and Decorator component interface.
 * This keeps services dependent on behavior rather than concrete item classes.
 */
public interface MenuItem {
    String getName();

    String getDescription();

    BigDecimal getPrice();
}
