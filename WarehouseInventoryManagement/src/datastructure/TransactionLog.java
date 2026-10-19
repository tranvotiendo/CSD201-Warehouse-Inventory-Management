package datastructure;

import model.StockTransaction;

/**
 *
 * @author tranvotiendo
 */
public class TransactionLog {
   private TransactionNode head;
    private TransactionNode tail;
    private int size;

    // TODO: Constructor, Getters & Setters

    public void recordTransaction(StockTransaction transaction) {
        // TODO: Thêm một bản ghi giao dịch mới vào danh sách liên kết
    }

    public void displayAllTransactions() {
        // TODO: Duyệt từ head đến tail để in toàn bộ lịch sử giao dịch
    }

    public void displayTransactionsByProduct(String barcode) {
        // TODO: Duyệt danh sách và in các giao dịch của một mã barcode cụ thể
    }

    public int calculateTotalByType(String barcode, String type) {
        // TODO: Tính tổng số lượng đã NHẬP (INBOUND) hoặc XUẤT (OUTBOUND) của một sản phẩm
        return 0;
    }
}
