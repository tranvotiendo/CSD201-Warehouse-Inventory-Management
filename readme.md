# 📦 Warehouse Inventory Management System (CSD201)

A Java Console-based Warehouse Inventory Management System focusing on the custom implementation and practical application of core data structures (**Binary Search Tree**, **FIFO Queue**, and **Singly Linked List**) to solve real-world problems in product catalog management, batch-level inventory tracking, and transaction history auditing.

---

## 👨‍💻 Project Information
* **Group:** 
* **Class ID:** SE2023
* **Course:** CSD201 - Data Structures and Algorithms in Java

---

## 🎯 Key Design Decisions & Custom Data Structures

This project strictly adheres to the requirement of manually implementing core data structures from scratch (without relying on Java's built-in collections such as `TreeMap`, `LinkedList`, or `Queue` for primary business logic):

1. **Binary Search Tree (`ProductBST`):**
   * Manages the product catalog and optimizes searching, insertion, updating, and deletion using `barcode` as the unique key.
   * **Strict Encapsulation:** Uses a `private static class Node` nested inside `ProductBST`. External classes (`Main`, `Manager` classes) cannot directly access or manipulate internal tree nodes, interacting strictly through safe `public` methods.
2. **Linked Queue (`BatchQueue` & `BatchNode`):**
   * Each `Product` maintains its own `BatchQueue` to manage multiple inventory batches (`Batch`).
   * Strictly enforces the **FIFO (First-In, First-Out)** policy: older batches received earlier are issued first. A batch is only removed (`dequeue`) from the queue when its remaining quantity reaches `0`.
3. **Singly Linked List (`TransactionLog` & `TransactionNode`):**
   * Records all stock-in (`INBOUND`) and stock-out (`OUTBOUND`) activities in chronological order for auditing and quantity reconciliation.
4. **Separation of Concerns & Data Consistency:**
   * Clearly separates user interaction (`ConsoleView`), program flow control (`Main`), and business coordination (`ProductManager`, `WarehouseManager`, `StatisticsManager`).
   * **Single Source of Truth:** `StatisticsManager` does not maintain a separate copy of stock data. Instead, it reads directly from the primary `ProductBST` and `TransactionLog` instances, ensuring 100% real-time data consistency immediately after any stock operation.

---

## 📂 Project Structure

```text
src/
├── constants/
│   └── StoreInfo.java           # Interface containing shared store and system constants
├── model/
│   ├── Product.java             # General product details & total stock calculation across batches
│   ├── Batch.java               # Batch-specific details, quantity validation, and updates
│   └── StockTransaction.java    # Record of a single stock-in or stock-out transaction
├── datastructure/
│   ├── ProductBST.java          # BST managing Products by barcode (contains private static class Node)
│   ├── BatchQueue.java          # FIFO Queue managing Batches of a single product
│   ├── BatchNode.java           # Node class for BatchQueue
│   ├── TransactionLog.java      # Singly Linked List storing transaction history
│   └── TransactionNode.java     # Node class for TransactionLog
├── manager/
│   ├── ProductManager.java      # Handles adding, updating, deleting, and searching products
│   ├── WarehouseManager.java    # Coordinates batch-based stock-in and FIFO stock-out operations
│   └── StatisticsManager.java   # Reads ProductBST & TransactionLog to generate reports and sort data
├── util/                        # (Optional) Input validation and helper utilities
│   └── InputValidator.java
├── view/                        
│   └── ConsoleView.java         # Displays menus, formatted tables, and collects user input
└── main/
    └── Main.java                # Initializes the system and coordinates between View and Managers