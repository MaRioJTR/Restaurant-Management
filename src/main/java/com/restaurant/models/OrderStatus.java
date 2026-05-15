package com.restaurant.models;

/**
 * Lifecycle states for an order.
 *
 * Role: Small domain enum that avoids fragile string status values.
 */
public enum OrderStatus {
    NEW,
    PLACED,
    CANCELLED,
    PAID,
    DONE
}
