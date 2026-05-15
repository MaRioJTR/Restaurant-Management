package com.restaurant.patterns.command;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * Invoker for order-related commands.
 *
 * Role: Queues and triggers command execution.
 * Pattern: GoF Command invoker. It is decoupled from concrete receivers such as
 * OrderService.
 */
public class OrderInvoker {
    private final Queue<Command> commandQueue = new ArrayDeque<>();

    public void submit(Command command) {
        commandQueue.offer(Objects.requireNonNull(command, "command"));
    }

    public void executeNext() {
        Command command = commandQueue.poll();
        if (command != null) {
            command.execute();
        }
    }

    public void executeAll() {
        while (!commandQueue.isEmpty()) {
            executeNext();
        }
    }
}
