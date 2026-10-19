package model;

import java.time.LocalDateTime;

/**
 *
 * @author tranvotiendo
 */
public class StockTransaction {

    private String transactionId;    // Mã giao dịch
    private TransactionType type;    // Loại giao dịch: INBOUND hoặc OUTBOUND
    private String barcode;          // Mã sản phẩm
    private String batchId;          // Mã lô hàng bị tác động
    private int quantity;            // Số lượng thay đổi
    private LocalDateTime timestamp; // Thời gian giao dịch
    private String note;             // Ghi chú thêm
    
    // TODO: Constructors, Getters & Setters
}
