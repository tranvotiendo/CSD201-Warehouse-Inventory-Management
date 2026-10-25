/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handle;

import controller.ProductManager;
import controller.WarehouseManager;
import model.Batch;
import util.InputValidator;
import view.ConsoleView;

/**
 *
 * @author Group4_SE2023
 */
public class HandleWarehouse {
    private ConsoleView view = new ConsoleView();
    private ProductManager productManager;
    private WarehouseManager warehouseManager;

    public HandleWarehouse() {
        this(new ProductManager(), new WarehouseManager());
    }

    public HandleWarehouse(ProductManager productManager, WarehouseManager warehouseManager) {
        this.productManager = productManager;
        this.warehouseManager = warehouseManager;
    }
    // ==========================================
    // MENU 2: STOCK IN / STOCK OUT
    // ==========================================
    public void handleWarehouseMenu() {
        boolean back = false;
        while (!back) {
            int choice = view.showWarehouseSubMenu();
            switch (choice) {
                case 1: { // Receive batch
                    String barcode = view.inputBarcodePrompt("receive stock");
                    if (productManager.findProductByBarcode(barcode) == null) {
                        view.showErrorMessage("Product does not exist. Please add the product first.");
                    } else {
                        String note = InputValidator.readNonEmptyString("Enter note: ");
                        Batch batch = view.inputNewBatchForm(barcode);
                        if (warehouseManager.receiveStock(barcode, batch, note)) {
                            view.showSuccessMessage("Batch received.");
                        } else {
                            view.showErrorMessage("Stock receipt failed (duplicate batch ID?).");
                        }
                    }
                    break;
                }
                case 2: { // Issue stock (FIFO)
                    String barcode = view.inputBarcodePrompt("issue stock");
                    int qty = InputValidator.readPositiveInt("Quantity to issue: ");
                    String note = InputValidator.readNonEmptyString("Enter note: ");
                    if (warehouseManager.issueStock(barcode, qty, note)) {
                        view.showSuccessMessage("Stock issued using FIFO.");
                    } else {
                        view.showErrorMessage("Insufficient stock or product not found.");
                    }
                    break;
                }
                case 0:
                    back = true;
                    break;
            }
        }
    }
    
}
