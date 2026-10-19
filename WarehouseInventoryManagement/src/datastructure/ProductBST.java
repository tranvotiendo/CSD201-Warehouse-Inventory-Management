package datastructure;

import model.Product;

/**
 *
 * @author tranvotiendo
 */
public class ProductBST {

    private Node head;
    private Node tail;
    
    

    // TODO: Constructor khởi tạo
    public boolean insert(Product product) {
        // TODO: Thêm sản phẩm mới vào BST theo barcode (dùng compareTo)
        return false;
    }

    private Node insertRec(Node current, Product product) {
        // TODO: Hàm đệ quy hỗ trợ insert
        return null;
    }

    public Product search(String barcode) {
        // TODO: Tìm kiếm và trả về Product theo barcode (trả về Product, KHÔNG trả về Node)
        return null;
    }

    private Node searchRec(Node current, String barcode) {
        // TODO: Hàm đệ quy hỗ trợ search
        return null;
    }

    public boolean delete(String barcode) {
        // TODO: Xóa sản phẩm khỏi BST theo barcode
        return false;
    }

    private Node deleteRec(Node current, String barcode) {
        // TODO: Hàm đệ quy hỗ trợ delete
        return null;
    }

    public void inOrderTraversal() {
        // TODO: Duyệt cây In-Order (LNR) và hiển thị thông tin sản phẩm
    }

    private void inOrderRec(Node current) {
        // TODO: Hàm đệ quy hỗ trợ duyệt In-Order
    }

    public boolean isEmpty() {
        return head == null;
    }
}
