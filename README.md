# Restaurant Management System

A desktop **JavaFX** application for managing restaurant operations such as menu browsing, order lifecycle handling, kitchen updates, and payments.

## Features

- JavaFX-based multi-screen UI (Dashboard, Orders, Order Details, Menu, Payment, Kitchen)
- Order lifecycle management (`NEW`, `PLACED`, `PAID`, `CANCELLED`, `DONE`)
- Built-in sample menu and order/cart management
- Payment handling with strategy-based payment methods
- Paid-order CSV export support
- Simple file-based persistence for order audit logs (`orders.txt`)

## Design Patterns Used

The project demonstrates several GoF design patterns:

- **Command** (`com.restaurant.patterns.command`)
- **Decorator** (`com.restaurant.patterns.decorator`)
- **Factory Method** (`com.restaurant.patterns.factory`)
- **Observer** (`com.restaurant.patterns.observer`)
- **Singleton** (`com.restaurant.patterns.singleton`)
- **State** (`com.restaurant.patterns.state`)
- **Strategy** (`com.restaurant.patterns.strategy`)

## Tech Stack

- Java 17
- JavaFX 21.0.5
- Maven

## Project Structure

```text
src/main/java/com/restaurant
├── app            # JavaFX entry point
├── controllers    # UI controllers
├── models         # Domain models
├── services       # Business services
├── patterns       # Design-pattern implementations
└── utils          # Shared utility classes

src/main/resources/com/restaurant/views
├── dashboard.fxml
├── kitchen.fxml
├── menu.fxml
├── order.fxml
├── order-details.fxml
└── payment.fxml
```

## Prerequisites

- JDK 17+
- Maven 3.8+

## Build

```bash
mvn -DskipTests package
```

## Test

```bash
mvn test
```

## Run the Application

```bash
mvn javafx:run
```

## Output Files

During runtime, the app may create:

- `orders.txt` (order audit lines)
- `paid_orders.csv` (paid order export data)

## Main Class

`com.restaurant.app.MainApp`
