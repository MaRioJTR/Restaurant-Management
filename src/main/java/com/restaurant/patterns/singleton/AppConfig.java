package com.restaurant.patterns.singleton;

/**
 * Application configuration singleton.
 *
 * Role: Owns shared configuration values that should be globally consistent
 * across the JavaFX application.
 * Pattern: GoF Singleton. The private constructor prevents direct creation,
 * while getInstance() provides one controlled global access point.
 */
public final class AppConfig {
    private static volatile AppConfig instance;

    private final String ordersFilePath;
    private final String paidOrdersCsvPath;
    private final String applicationTheme;

    private AppConfig() {
        this.ordersFilePath = "orders.txt";
        this.paidOrdersCsvPath = "paid_orders.csv";
        this.applicationTheme = "default";
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    public String getOrdersFilePath() {
        return ordersFilePath;
    }

    public String getPaidOrdersCsvPath() {
        return paidOrdersCsvPath;
    }

    public String getApplicationTheme() {
        return applicationTheme;
    }
}
