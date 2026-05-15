# Restaurant Management System

A JavaFX desktop application for managing restaurant operations, including menu selection, order tracking, kitchen workflow, and payment processing.

## Features

- Create and manage orders from a menu
- Assign and track table usage (1–10)
- Apply item customizations (extra cheese, no onions, extra sauce)
- Monitor order lifecycle: `NEW → PLACED → PAID → DONE` (or `CANCELLED`)
- Process payments with multiple methods (card, cash, wallet)
- View live updates across dashboard, orders, and kitchen screens
- Persist order/payment audit records to local files

## Tech Stack

- Java 17
- JavaFX 21.0.5 (`javafx-controls`, `javafx-fxml`)
- Maven

## Project Structure

- `src/main/java/com/restaurant/app` – application entry point
- `src/main/java/com/restaurant/controllers` – JavaFX controllers (UI behavior)
- `src/main/java/com/restaurant/models` – domain models
- `src/main/java/com/restaurant/services` – business services
- `src/main/java/com/restaurant/repositories` – file-based persistence adapters
- `src/main/java/com/restaurant/patterns` – design-pattern implementations
- `src/main/resources/com/restaurant/views` – FXML views

## Prerequisites

- JDK 17+
- Maven 3.9+

## Build and Run

```bash
mvn clean compile
mvn javafx:run
```

Or package as a JAR:

```bash
mvn -DskipTests package
```

## Data Files

The app writes runtime records in the project root:

- `orders.txt` – order audit trail
- `paid_orders.csv` – paid-order receipts

## Additional Documentation

See [DOCUMENTATION.md](./DOCUMENTATION.md) for architecture, module-level details, design patterns, and workflows.
