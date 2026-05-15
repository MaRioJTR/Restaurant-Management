package com.restaurant.patterns.command;

/**
 * Command abstraction for user/business actions.
 *
 * Role: Encapsulates an executable request.
 * Pattern: GoF Command. Invokers call this interface without knowing the
 * receiver or business details.
 */
public interface Command {
    void execute();
}
