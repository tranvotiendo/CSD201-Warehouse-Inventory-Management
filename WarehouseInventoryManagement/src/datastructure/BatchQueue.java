package datastructure;

import model.Batch;

/**
 *
 * @author tranvotiendo
 */
public class BatchQueue {

    private BatchNode front;
    private BatchNode rear;
    private int size;

    // TODO: Constructor, Getters & Setters
    public void enqueue(Batch batch) {
        // TODO: Thêm lô hàng mới vào cuối hàng đợi (rear)
    }

    public Batch dequeue() {
        // TODO: Xóa và trả về lô hàng ở đầu hàng đợi (front) khi số lượng lô đã về 0
        return null;
    }

    public Batch peek() {
        // TODO: Xem lô hàng đầu tiên (lô cũ nhất) mà không xóa khỏi hàng đợi
        return null;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int getTotalQuantityInQueue() {
        // TODO: Duyệt từ front đến rear để cộng tổng quantity của tất cả các lô hiện có
        return 0;
    }

    public void displayBatches() {
        // TODO: In danh sách các lô hàng đang tồn trong Queue
    }
}
