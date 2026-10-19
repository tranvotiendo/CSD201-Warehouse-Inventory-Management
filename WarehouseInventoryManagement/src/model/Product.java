package model;

import datastructure.BatchQueue;

/**
 *
 * @author tranvotiendo
 */
public class Product {

    private String barcode;          // Mã định danh (Khóa chính để so sánh trong BST)
    private String name;             // Tên sản phẩm
    private String category;         // Danh mục
    private String unit;             // Đơn vị tính (Thùng, Hộp, Cái...)
    private double price;            // Giá bán niêm yết
    private int totalQuantity;       // Tổng tồn kho (Tổng quantity của các Batch trong Queue)
    private BatchQueue batchQueue;   // Hàng đợi chứa các lô hàng của sản phẩm này

    // TODO: Constructors (nhớ khởi tạo this.batchQueue = new BatchQueue()), Getters & Setters

    // Hàm tính tổng tồn kho từ các lô hàng (Entry #003)
    public int calculateTotalQuantity() {
        // TODO: Gọi hàm tính tổng số lượng các lô đang có trong batchQueue
        return 0;
    }
    
    public Product() {
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSellingPrice() {
        return price;
    }

    public void setSellingPrice(double price) {
        this.price = price;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
