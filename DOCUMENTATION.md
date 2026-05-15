# Restaurant Management System Documentation

## 1. Overview

This project is a JavaFX-based desktop system for restaurant operations.  
It supports menu browsing, cart/order creation, table assignment, kitchen tracking, and payment processing using multiple design patterns.

## 2. Architecture

The application follows an MVC-oriented structure:

- **Model (`com.restaurant.models`)**: domain objects (`Order`, `MenuItem`, `PaymentReceipt`, `OrderStatus`)
- **View (`src/main/resources/com/restaurant/views`)**: FXML screens
- **Controller (`com.restaurant.controllers`)**: UI event handling and navigation
- **Service (`com.restaurant.services`)**: business behavior and use-case coordination
- **Repository (`com.restaurant.repositories`)**: persistence to text/CSV files

`MainApp` bootstraps JavaFX and loads the dashboard as the initial screen.

## 3. Main Modules

### 3.1 UI and Navigation

- `MainApp`: application entry point
- `NavigationManager`: view switching
- Controllers:
  - `DashboardController`
  - `MenuController`
  - `OrderController`
  - `OrderDetailsController`
  - `PaymentController`
  - `KitchenController`

### 3.2 Shared UI Context

`RestaurantUiContext` is a singleton-like coordinator for shared UI state:

- Shared `ObservableList<Order>` and cart items
- Shared service instances (`OrderService`, `MenuService`, `PaymentService`)
- Selected order management
- Order upsert logic used by multiple screens

### 3.3 Services

- `MenuService`: exposes menu items and applies decorators for customizations
- `OrderService`: handles order lifecycle operations and observer notifications
- `PaymentService`: runs strategy-based payment and writes payment receipts

### 3.4 Persistence

- `FileOrderRepository`: appends order audit lines to `orders.txt`
- `FilePaidOrderCsvRepository`: appends paid receipt rows to `paid_orders.csv`
- `AppConfig`: centralized config for file paths and app-wide settings

## 4. Order Lifecycle

`Order` uses state transitions through pattern-based state objects:

- Starts in `NEW`
- Can move to `PLACED`
- Can be `PAID`
- Can be completed as `DONE`
- Can be moved to `CANCELLED` where allowed

The lifecycle controls table blocking and available UI actions.

## 5. Design Patterns Used

- **Singleton**: `AppConfig`, `RestaurantUiContext` shared instances
- **Factory Method**: menu item creation (`FoodFactory`, `DrinkFactory`, `DessertFactory`)
- **Decorator**: menu customizations (`ExtraCheese`, `NoOnions`, `ExtraSauce`)
- **Command**: encapsulated order actions (`PlaceOrderCommand`, `PayOrderCommand`, etc.)
- **Observer**: UI updates on order changes (`OrderService` subject + observers)
- **Strategy**: payment method swapping (`CreditCardPayment`, `CashPayment`, `WalletPayment`)
- **State**: order status transitions (`NewState`, `PlacedState`, `PaidState`, etc.)

## 6. User Workflow

1. Open app (dashboard loads)
2. Go to **Menu** and add/customize items
3. Select available table and place order
4. Track/update order from **Dashboard** or **Orders**
5. Process payment in **Payments**
6. Mark paid orders as done in **Kitchen**

## 7. Build and Run

```bash
mvn clean compile
mvn javafx:run
```

Package:

```bash
mvn -DskipTests package
```

## 8. Notes

- No automated tests are currently defined (`mvn test` reports no tests to run).
- Runtime records are persisted locally in project root files:
  - `orders.txt`
  - `paid_orders.csv`
