package main;

import handle.HandleProduct;
import handle.HandleStatistics;
import handle.HandleWarehouse;
import util.InputValidator;
import view.ConsoleView;

/**
 *
 * @author Group4_SE2023
 */
public class Main {
private static final ConsoleView view = new ConsoleView();
private static final HandleProduct handleProduct = new HandleProduct();
private static final HandleWarehouse handleWarehouse = new HandleWarehouse();
private static final HandleStatistics handleStatistics = new HandleStatistics();

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            int choice = view.showMainMenuAndGetChoice();
            switch (choice) {
                case 1:
                    handleProduct.handleProductMenu();
                case 2:
                    handleWarehouse.handleWarehouseMenu();
                case 3:
                    handleStatistics.handleStatisticsMenu();
                case 0: {
                    view.showSuccessMessage("Goodbye!");
                    running = false;
                }
            }
        }
    }

    

    
    
}
