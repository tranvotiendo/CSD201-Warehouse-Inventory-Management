package handle;

import java.util.ArrayList;
import java.util.List;
import controller.ProductManager;
import java.io.IOException;
import java.nio.file.Paths;
import model.Product;
import util.InputValidator;
import util.InventoryFileManager;
import view.ConsoleView;

/**
 *
 * @author Group4_SE2023
 */
public class HandleProduct {

    private ConsoleView view = new ConsoleView();
    private ProductManager productManager;

    public HandleProduct() {
        this(new ProductManager());
    }

    public HandleProduct(ProductManager productManager) {
        this.productManager = productManager;
    }
    // ==========================================
    // MENU 1: PRODUCT MANAGEMENT
    // ==========================================

    public void handleProductMenu() {
        boolean back = false;
        while (!back) {
            int choice = view.showProductSubMenu();
            switch (choice) {
                case 1: { // Add
                    Product p = view.inputNewProductForm();
                    if (productManager.addProduct(p)) {
                        view.showSuccessMessage("Product added.");
                    } else {
                        view.showErrorMessage("Barcode already exists!");
                    }
                    break;
                }
                case 2: { // Update
                    String barcode = view.inputBarcodePrompt("update");
                    Product existing = productManager.findProductByBarcode(barcode);
                    if (existing == null) {
                        view.showErrorMessage("Product not found.");
                    } else {
                        List<Product> listProduct = new ArrayList<>();
                        listProduct.add(existing);
                        view.printProducts(listProduct);

                        System.out.println("(Press Enter to keep the current value)");
                        String newName = InputValidator.readString("Enter new name: ");
                        String newCategory = InputValidator.readString("Enter new category: ");
                        String newUnit = InputValidator.readString("Enter new unit: ");

                        double newPrice = -1; // -1 = keep current price
                        String priceInput = InputValidator.readString("Enter new price: ");
                        if (!priceInput.isEmpty()) {
                            try {
                                double parsed = Double.parseDouble(priceInput);
                                if (parsed > 0) {
                                    newPrice = parsed;
                                } else {
                                    view.showErrorMessage("Price must be > 0. Keeping the current price.");
                                }
                            } catch (NumberFormatException e) {
                                view.showErrorMessage("Invalid price. Keeping the current price.");
                            }
                        }

                        if (productManager.updateProduct(barcode, newName, newCategory, newUnit, newPrice)) {
                            view.showSuccessMessage("Product updated.");
                        } else {
                            view.showErrorMessage("Update failed.");
                        }
                    }
                    break;
                }
                case 3: { // Delete
                    String barcode = view.inputBarcodePrompt("delete");
                    Product deleteProduct = productManager.findProductByBarcode(barcode);
                    if (deleteProduct != null) {
                        if (deleteProduct.calculateTotalQuantity() > 0) {
                            view.showErrorMessage("Cannot delete: Product still has " + deleteProduct.calculateTotalQuantity() + " items in stock.");
                        } else if (productManager.deleteProduct(barcode)) {
                            view.showSuccessMessage("Product deleted.");
                        } else {
                            view.showErrorMessage("Delete failed.");
                        }
                    } else {
                        view.showErrorMessage("Product not found.");
                    }
                    break;
                }
                case 4: { // Search
                    String barcode = view.inputBarcodePrompt("search");
                    Product product = productManager.findProductByBarcode(barcode);

                    if (product == null) {
                        view.showErrorMessage("Product not found.");
                    } else {
                        List<Product> listProduct = new ArrayList<>();
                        listProduct.add(product);          // add product to list
                        view.printProducts(listProduct);
                        view.printBatches(product.getBatchQueue());
                    }
                    break;
                }
                case 5:
                    view.printProducts(productManager.getAllProducts()); // View all
                    break;
                case 6: {
                    String filePath = InputValidator.readNonEmptyString("CSV file path to import: ");
                    try {
                        int imported = InventoryFileManager.importInventory(Paths.get(filePath), productManager);
                        view.showSuccessMessage("Imported " + imported + " product(s) with current batches.");
                    } catch (IOException | IllegalArgumentException ex) {
                        view.showErrorMessage("Import failed: " + ex.getMessage());
                    }
                    break;
                }
                case 7: {
                    String filePath = InputValidator.readNonEmptyString("CSV file path to export: ");
                    try {
                        int exported = InventoryFileManager.exportInventory(
                                Paths.get(filePath), productManager.getAllProducts());
                        view.showSuccessMessage("Exported " + exported + " product(s) with current batches.");
                    } catch (IOException | IllegalArgumentException ex) {
                        view.showErrorMessage("Export failed: " + ex.getMessage());
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
