package com.restaurant.models;

import com.restaurant.patterns.state.NewState;
import com.restaurant.patterns.state.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Aggregate root for a restaurant order.
 *
 * Role: Owns ordered items, table number, status, and total calculation.
 * Pattern relationship: Used by Command, Observer, and Strategy contexts through
 * composition rather than UI-level hardcoding.
 */
public class Order {
    private final UUID id;
    private int tableNumber;
    private final List<MenuItem> items;
    private final LocalDateTime createdAt;
    private OrderState state;

    public Order(int tableNumber, List<MenuItem> items) {
        this.id = UUID.randomUUID();
        this.tableNumber = tableNumber;
        this.items = new ArrayList<>(Objects.requireNonNull(items, "items"));
        this.createdAt = LocalDateTime.now();
        this.state = new NewState();
    }

    public UUID getId() {
        return id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void assignTable(int tableNumber) {
        if (tableNumber < 1 || tableNumber > 10) {
            throw new IllegalArgumentException("Table number must be between 1 and 10.");
        }
        this.tableNumber = tableNumber;
    }

    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return state.getStatus();
    }

    public void changeState(OrderState state) {
        this.state = Objects.requireNonNull(state, "state");
    }

    public void place() {
        state.place(this);
    }

    public void pay() {
        state.pay(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void done() {
        state.done(this);
    }

    public void complete() {
        state.complete(this);
    }

    public boolean blocksTable() {
        return state.blocksTable();
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(MenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
