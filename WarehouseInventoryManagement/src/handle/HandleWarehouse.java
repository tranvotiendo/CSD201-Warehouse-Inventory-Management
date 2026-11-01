package handle;

import controller.ProductManager;
import controller.WarehouseManager;
import java.time.LocalDate;
import java.util.List;
import model.Batch;
import util.InputValidator;
import view.ConsoleView;

/**
 *
 * @author Group4_SE2023
 */
public class HandleWarehouse {

    private ConsoleView view = new ConsoleView();
    private ProductManager productManager = new ProductManager();
    private WarehouseManager warehouseManager = new WarehouseManager();

    public HandleWarehouse() {
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
                            view.showErrorMessage("Stock receipt failed (duplicate batch ID or invalid batch data).");
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
                case 3: { // Remove expired batches
                    String barcode = view.inputBarcodePrompt("remove expired batches");
                    if (productManager.findProductByBarcode(barcode) == null) {
                        view.showErrorMessage("Product not found.");
                        break;
                    }
                    List<Batch> removed = warehouseManager.processExpiredBatches(barcode, LocalDate.now());
                    if (removed.isEmpty()) {
                        view.showSuccessMessage("No expired batches found for this product.");
                    } else {
                        System.out.println("Removed expired batches:");
                        view.printBatchHeader();
                        for (Batch b : removed) {
                            view.printBatchRow(b);
                        }
                        view.showSuccessMessage("Removed " + removed.size()
                                + " expired batch(es). EXPIRED transactions recorded.");
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
