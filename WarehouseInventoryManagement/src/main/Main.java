package main;

import handle.HandleProduct;
import handle.HandleStatistics;
import handle.HandleWarehouse;
import controller.ProductManager;
import controller.StatisticsManager;
import controller.WarehouseManager;
import datastructure.ProductBST;
import datastructure.TransactionLog;
import view.ConsoleView;

/**
 *
 * @author Group4_SE2023
 */
public class Main {
private static final ConsoleView view = new ConsoleView();
private static final ProductBST productTree = new ProductBST();
private static final TransactionLog transactionLog = new TransactionLog();
private static final ProductManager productManager = new ProductManager(productTree);
private static final HandleProduct handleProduct = new HandleProduct(productManager);
private static final HandleWarehouse handleWarehouse = new HandleWarehouse(
        productManager, new WarehouseManager(productTree, transactionLog));
private static final HandleStatistics handleStatistics = new HandleStatistics(
        new StatisticsManager(productTree, transactionLog));

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            int choice = view.showMainMenuAndGetChoice();
            switch (choice) {
                case 1:
                    handleProduct.handleProductMenu();
                    break;
                case 2:
                    handleWarehouse.handleWarehouseMenu();
                    break;
                case 3:
                    handleStatistics.handleStatisticsMenu();
                    break;
                case 0: {
                    view.showSuccessMessage("Goodbye!");
                    running = false;
                    break;
                }
            }
        }
    }

    

    
    
}
