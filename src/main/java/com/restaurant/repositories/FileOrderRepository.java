package com.restaurant.repositories;

import com.restaurant.models.Order;
import com.restaurant.patterns.singleton.AppConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FileOrderRepository implements OrderRepository {
    private final AppConfig config;
    private final Map<UUID, Order> orderStore = new ConcurrentHashMap<>();

    public FileOrderRepository() {
        this(AppConfig.getInstance());
    }

    public FileOrderRepository(AppConfig config) {
        this.config = config;
    }

    @Override
    public void save(Order order) {
        orderStore.put(order.getId(), order);
        appendAuditLine(order);
    }

    @Override
    public Optional<Order> findById(UUID orderId) {
        return Optional.ofNullable(orderStore.get(orderId));
    }

    private void appendAuditLine(Order order) {
        String row = order.getId() + "|table=" + order.getTableNumber()
                + "|status=" + order.getStatus()
                + "|total=" + order.getTotal()
                + System.lineSeparator();
        try {
            Files.writeString(
                    Path.of(config.getOrdersFilePath()),
                    row,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to persist order audit record.", exception);
        }
    }
}
