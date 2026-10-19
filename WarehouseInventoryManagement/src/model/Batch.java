package model;

import java.time.LocalDate;

/**
 *
 * @author tranvotiendo
 */
public class Batch {

    private String batchId;          // Mã lô hàng
    private String barcode;          // Mã vạch sản phẩm thuộc lô này
    private int quantity;            // Số lượng tồn hiện tại trong lô
    private double importPrice;      // Giá nhập của lô này
    private LocalDate manufacturingDate; // Ngày sản xuất
    private LocalDate expiryDate;        // Hạn sử dụng
    private LocalDate importDate;        // Ngày nhập kho
    
    // TODO: Constructors, Getters & Setters

    // Các hàm nghiệp vụ của riêng lô hàng (Entry #003)
    public boolean isValidQuantity(int amount) {
        // TODO: Kiểm tra số lượng hợp lệ (amount > 0 và phù hợp với tồn hiện tại)
        return false;
    }

    public void reduceQuantity(int amount) {
        // TODO: Giảm số lượng khi xuất kho một phần lô hàng (Entry #002)
    }

    public boolean isEmptyBatch() {
        // TODO: Kiểm tra xem quantity == 0 hay chưa để biết có dequeue khỏi hàng đợi không
        return false;
    }

    public boolean isExpired() {
        // TODO: Kiểm tra lô hàng đã hết hạn chưa (so với LocalDate.now())
        return false;
    }
}
