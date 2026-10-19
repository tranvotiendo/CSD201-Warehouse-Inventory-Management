package view;

import model.Batch;
import model.Product;
import model.StockTransaction;

/**
 *
 * @author tranvotiendo
 */
public class ConsoleView {

    // ==========================================
    // 1. NHÓM HÀM HIỂN THỊ MENU
    // ==========================================
    public int showMainMenuAndGetChoice() {
        // TODO: In tiêu đề (lấy từ StoreInfo.STORE_NAME), in các mục chọn:
        // 1. Quản lý sản phẩm (ProductManager)
        // 2. Nhập / Xuất kho theo lô (WarehouseManager)
        // 3. Thống kê & Báo cáo (StatisticsManager)
        // 0. Thoát
        // Gọi InputValidator để đọc và trả về lựa chọn
        return 0;
    }

    public int showProductSubMenu() {
        // TODO: Menu con: 1. Thêm SP, 2. Cập nhật SP, 3. Xóa SP, 4. Tìm kiếm SP, 5. Xem toàn bộ SP
        return 0;
    }

    public int showWarehouseSubMenu() {
        // TODO: Menu con: 1. Nhập lô hàng mới (Receive Stock), 2. Xuất hàng FIFO (Issue Stock)
        return 0;
    }

    public int showStatisticsSubMenu() {
        // TODO: Menu con: 1. Báo cáo tồn kho, 2. Kiểm tra lô hết hạn/sắp hết hàng, 3. Sắp xếp SP theo tồn kho, 4. Lịch sử giao dịch
        return 0;
    }

    // ==========================================
    // 2. NHÓM HÀM NHẬP FORM DỮ LIỆU TỪ NGƯỜI DÙNG
    // ==========================================
    public Product inputNewProductForm() {
        // TODO: Dùng InputValidator yêu cầu nhập barcode, name, category, unit, price
        // Khởi tạo và trả về đối tượng Product mới
        return null;
    }

    public Batch inputNewBatchForm(String barcode) {
        // TODO: Dùng InputValidator yêu cầu nhập batchId, quantity, importPrice, mfgDate, expDate
        // Khởi tạo và trả về đối tượng InventoryBatch mới
        return null;
    }

    public String inputBarcodePrompt(String actionName) {
        // TODO: Hiển thị lời nhắc nhập mã barcode cần tìm/xóa/nhập/xuất
        return null;
    }

    // ==========================================
    // 3. NHÓM HÀM HIỂN THỊ BẢNG & THÔNG BÁO
    // ==========================================
    public void printProductHeader() {
        // TODO: In dòng tiêu đề bảng Sản phẩm (Barcode | Tên | Danh mục | ĐVT | Giá | Tổng tồn)
    }

    public void printProductRow(Product product) {
        // TODO: Định dạng (String.format / printf) để in 1 dòng thông tin Product
    }

    public void printBatchRow(Batch batch) {
        // TODO: Định dạng in 1 dòng thông tin Lô hàng (BatchID | Số lượng còn | NSX | HSD)
    }

    public void printTransactionRow(StockTransaction tx) {
        // TODO: Định dạng in 1 dòng lịch sử giao dịch
    }

    public void showSuccessMessage(String message) {
        // TODO: In thông báo thành công
    }

    public void showErrorMessage(String error) {
        // TODO: In thông báo lỗi (ví dụ: không đủ tồn kho, trùng barcode...)
    }
}
