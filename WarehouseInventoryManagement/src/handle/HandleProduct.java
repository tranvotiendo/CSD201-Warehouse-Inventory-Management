/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
                    Product existing = productManager.findProductByBarcode(barcode); // renamed to match
                    if (existing == null) {
                        view.showErrorMessage("Product not found.");
                    } else {
                        List<Product> listProduct = new ArrayList<>();
                        listProduct.add(existing);          // add product to list
                        view.printProducts(listProduct);
                        String newName = InputValidator.readNonEmptyString("Enter new name: ");
                        String newCategory = InputValidator.readNonEmptyString("Enter new category: ");
                        String newUnit = InputValidator.readNonEmptyString("Enter new unit: ");
                        double newPrice = InputValidator.readPositiveDouble("Enter new price: ");

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
                    if (productManager.deleteProduct(barcode)) {
                        view.showSuccessMessage("Product deleted.");
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
