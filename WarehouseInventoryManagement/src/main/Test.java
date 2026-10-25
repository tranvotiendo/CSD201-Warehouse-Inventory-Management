package main;

import constants.StoreInfo;
import controller.ProductManager;
import controller.StatisticsManager;
import controller.WarehouseManager;
import datastructure.BatchNode;
import datastructure.BatchQueue;
import datastructure.ProductBST;
import datastructure.TransactionLog;
import java.time.LocalDate;
import java.util.List;
import model.Batch;
import model.Product;
import model.StockTransaction;
import model.TransactionType;
import view.ConsoleView;

/**
 * Comprehensive Automated Test Suite for Warehouse Inventory Management System.
 * Tests all business logic in ProductManager, WarehouseManager, and
 * StatisticsManager, as well as underlying data structures (ProductBST,
 * BatchQueue, TransactionLog).
 *
 * @author Group4_SE2023
 */
public class Test {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("=========================================================================================");
        System.out.println("                 TESTING SUITE: WAREHOUSE INVENTORY MANAGEMENT SYSTEM                    ");
        System.out.println("                        " + StoreInfo.STORE_NAME);
        System.out.println("=========================================================================================\n");

        // 1. Khoi tao shared data structures
        ProductBST productTree = new ProductBST();
        TransactionLog transactionLog = new TransactionLog();

        // 2. Khoi tao cac controller
        ProductManager productManager = new ProductManager(productTree);
        WarehouseManager warehouseManager = new WarehouseManager(productTree, transactionLog);
        StatisticsManager statisticsManager = new StatisticsManager(productTree, transactionLog);
        ConsoleView consoleView = new ConsoleView();

        // 3. Chay cac nhom kiem thu
        testProductManager(productManager, warehouseManager, consoleView);
        testWarehouseManager(productManager, warehouseManager, transactionLog, consoleView);
        testStatisticsManager(statisticsManager, warehouseManager, productManager);
        testDataStructuresAndEdgeCases(productTree, transactionLog);

        // 4. Tong ket ket qua kiem thu
        printTestSummary();
    }

    // =========================================================================
    // PHAN 1: KIEM THU PRODUCT MANAGER (QUAN LY SAN PHAM)
    // =========================================================================
    private static void testProductManager(ProductManager pm, WarehouseManager wm, ConsoleView cv) {
        printSectionHeader("PHAN 1: KIEM THU NGHIEP VU PRODUCT MANAGER");

        // 1.1 Them san pham moi hop le
        System.out.println("--> [1.1] Them cac san pham hop le vao he thong (addProduct):");
        Product p1 = new Product("P001", "Sua tuoi Vinamilk", "Thuc pham", "hop", 12000);
        Product p2 = new Product("P002", "Gao ST25", "Nong san", "kg", 35000);
        Product p3 = new Product("P003", "Dau an Simply 1L", "Gia vi", "chai", 55000);
        Product p4 = new Product("P004", "Mi Hao Hao chua cay", "Thuc pham", "goi", 4500);
        Product p5 = new Product("P005", "Banh Snack Oishi", "Do an vat", "goi", 6000);
        Product pTemp = new Product("TEMP", "San pham tam thoi", "Khac", "cai", 10000);

        assertTest("Them P001 (Sua tuoi)", pm.addProduct(p1), true);
        assertTest("Them P002 (Gao ST25)", pm.addProduct(p2), true);
        assertTest("Them P003 (Dau an Simply)", pm.addProduct(p3), true);
        assertTest("Them P004 (Mi Hao Hao)", pm.addProduct(p4), true);
        assertTest("Them P005 (Banh Snack)", pm.addProduct(p5), true);
        assertTest("Them TEMP (San pham tam)", pm.addProduct(pTemp), true);

        // 1.2 Kiem tra them san pham vi pham validation
        System.out.println("\n--> [1.2] Kiem tra them san pham khong hop le / trung lap:");
        Product pDuplicate = new Product("P001", "Sua tuoi gia mao", "Thuc pham", "hop", 15000);
        assertTest("Tu choi barcode bi trung (P001 da ton tai)", pm.addProduct(pDuplicate), false);

        Product pNullBarcode = new Product(null, "Khong ma vach", "Loi", "cai", 5000);
        assertTest("Tu choi san pham co barcode null", pm.addProduct(pNullBarcode), false);

        Product pEmptyBarcode = new Product("   ", "Barcode rong", "Loi", "cai", 5000);
        assertTest("Tu choi san pham co barcode trong", pm.addProduct(pEmptyBarcode), false);

        assertTest("Tu choi san pham null hoan toan", pm.addProduct(null), false);

        // 1.3 Kiem tra tim kiem san pham (findProductByBarcode & hasProduct)
        System.out.println("\n--> [1.3] Kiem tra tim kiem san pham theo barcode:");
        Product foundP1 = pm.findProductByBarcode("P001");
        assertTest("Tim kiem P001 ton tai", foundP1 != null && foundP1.getName().equals("Sua tuoi Vinamilk"), true);
        assertTest("Kiem tra hasProduct(\"P002\")", pm.hasProduct("P002"), true);

        Product foundNotExist = pm.findProductByBarcode("P999");
        assertTest("Tim kiem san pham khong ton tai (P999)", foundNotExist == null, true);
        assertTest("Kiem tra hasProduct(\"P999\")", pm.hasProduct("P999"), false);
        assertTest("Tim kiem voi barcode null hoac trong", pm.findProductByBarcode("  ") == null, true);

        // 1.4 Kiem tra cap nhat thong tin san pham (updateProduct)
        System.out.println("\n--> [1.4] Kiem tra cap nhat thong tin san pham (updateProduct):");
        boolean updatedP2 = pm.updateProduct("P002", "Gao ST25 Thuong Hang", "Nong san sach", "tui 5kg", 180000);
        Product checkP2 = pm.findProductByBarcode("P002");
        assertTest("Cap nhat P002 thanh cong", updatedP2, true);
        assertTest("Gia tri sau cap nhat cua P002",
                checkP2 != null && checkP2.getName().equals("Gao ST25 Thuong Hang")
                && checkP2.getPrice() == 180000.0 && checkP2.getUnit().equals("tui 5kg"), true);

        boolean updateNotExist = pm.updateProduct("P999", "Ten moi", "DanhMuc", "cai", 10000);
        assertTest("Tu choi cap nhat san pham khong ton tai (P999)", updateNotExist, false);

        // Cap nhat giu nguyen cac truong null/rong
        pm.updateProduct("P002", null, null, null, 175000);
        assertTest("Cap nhat rieng gia ban, giu nguyen ten/danh muc/unit",
                checkP2.getPrice() == 175000.0 && checkP2.getName().equals("Gao ST25 Thuong Hang"), true);

        // 1.5 Kiem tra xoa san pham (deleteProduct)
        System.out.println("\n--> [1.5] Kiem tra xoa san pham theo rang buoc nghiep vu (deleteProduct):");
        // San pham TEMP chua nhap kho (ton kho = 0) -> Cho phep xoa
        assertTest("Xoa san pham TEMP co ton kho = 0 (Thanh cong)", pm.deleteProduct("TEMP"), true);
        assertTest("Xac nhan TEMP da bi xoa khoi he thong", pm.hasProduct("TEMP"), false);

        // Xoa san pham khong ton tai
        assertTest("Tu choi xoa san pham khong ton tai", pm.deleteProduct("NON_EXIST"), false);
        assertTest("Tu choi xoa voi barcode null/trong", pm.deleteProduct("   "), false);

        // 1.6 Hien thi toan bo san pham trong cay BST (In-order traversal)
        System.out.println("\n--> [1.6] Hien thi toan bo san pham (BST In-order):");
        List<Product> allProducts = pm.getAllProducts();
        assertTest("Danh sach san pham khong rong", allProducts != null && !allProducts.isEmpty(), true);
        pm.displayAllProducts();
    }

    // =========================================================================
    // PHAN 2: KIEM THU WAREHOUSE MANAGER (NHAP / XUAT KHO FIFO / HET HAN)
    // =========================================================================
    private static void testWarehouseManager(ProductManager pm, WarehouseManager wm, TransactionLog tl, ConsoleView cv) {
        printSectionHeader("PHAN 2: KIEM THU NGHIEP VU WAREHOUSE MANAGER");
        LocalDate today = LocalDate.now();

        // 2.1 Nhap kho lo hang moi (receiveStock)
        System.out.println("--> [2.1] Nhap kho cac lo hang vao BatchQueue (receiveStock):");

        // San pham P001 nhap 2 lo:
        // Lo 1: 20 hop, HSD con 120 ngay
        Batch b1_P1 = new Batch("B101", "P001", 20, 10000, today.minusDays(10), today.plusDays(120), today);
        // Lo 2: 30 hop, HSD con 180 ngay
        Batch b2_P1 = new Batch("B102", "P001", 30, 10500, today.minusDays(5), today.plusDays(180), today);

        assertTest("Nhap Lo B101 cho P001 (SL: 20)", wm.receiveStock("P001", b1_P1, "Nhap kho dot 1"), true);
        assertTest("Nhap Lo B102 cho P001 (SL: 30)", wm.receiveStock("P001", b2_P1, "Nhap kho dot 2"), true);
        assertTest("Tong ton kho P001 sau 2 lo = 50", pm.findProductByBarcode("P001").calculateTotalQuantity() == 50, true);

        // San pham P002: nhap 1 lo 50 tui
        Batch b1_P2 = new Batch("B201", "P002", 50, 150000, today.minusDays(15), today.plusDays(90), today);
        assertTest("Nhap Lo B201 cho P002 (SL: 50)", wm.receiveStock("P002", b1_P2, "Nhap hang gao"), true);

        // San pham P003: nhap 1 lo 15 chai, HSD con 20 ngay (sap het han <= 30 ngay)
        Batch b1_P3 = new Batch("B301", "P003", 15, 45000, today.minusDays(30), today.plusDays(20), today);
        assertTest("Nhap Lo B301 cho P003 (SL: 15, HSD con 20 ngay)", wm.receiveStock("P003", b1_P3, "Nhap dau an"), true);

        // San pham P004: nhap 1 lo 10 goi, HSD da qua 5 ngay (da het han!)
        Batch b1_P4 = new Batch("B401", "P004", 10, 3500, today.minusDays(90), today.minusDays(5), today.minusDays(60));
        assertTest("Nhap Lo B401 cho P004 (SL: 10, Da het han 5 ngay)", wm.receiveStock("P004", b1_P4, "Lo hang cu"), true);

        // San pham P005: nhap 1 lo 8 goi (ton kho thap <= 10)
        Batch b1_P5 = new Batch("B501", "P005", 8, 4500, today.minusDays(10), today.plusDays(60), today);
        assertTest("Nhap Lo B501 cho P005 (SL: 8 - Canh bao ton thap)", wm.receiveStock("P005", b1_P5, "Nhap snack"), true);

        // 2.2 Kiem tra cac truong hop nhap kho khong hop le
        System.out.println("\n--> [2.2] Kiem tra tu choi nhap kho du lieu khong hop le:");
        Batch bInvalidQty = new Batch("BINV", "P001", 0, 10000, today, today.plusDays(30), today);
        assertTest("Tu choi nhap kho voi so luong <= 0", wm.receiveStock("P001", bInvalidQty, "Loi SL 0"), false);

        Batch bNegativeQty = new Batch("BINV2", "P001", -5, 10000, today, today.plusDays(30), today);
        assertTest("Tu choi nhap kho voi so luong am", wm.receiveStock("P001", bNegativeQty, "Loi SL am"), false);

        Batch bValid = new Batch("B999", "P999", 10, 10000, today, today.plusDays(30), today);
        assertTest("Tu choi nhap kho cho barcode khong ton tai (P999)", wm.receiveStock("P999", bValid, "Loi ma SP"), false);
        assertTest("Tu choi nhap kho voi batch null", wm.receiveStock("P001", null, "Loi batch null"), false);

        // 2.3 Kiem tra xem danh sach lo hang cua san pham
        System.out.println("\n--> [2.3] Kiem tra getProductBatches & hien thi lo hang P001:");
        BatchQueue p1Batches = wm.getProductBatches("P001");
        assertTest("P001 co dung 2 lo hang trong queue", p1Batches != null && p1Batches.getSize() == 2, true);
        System.out.println("Danh sach lo hang hien tai cua P001:");
        cv.printBatches(p1Batches);

        // 2.4 Kiem tra xuat kho theo FIFO (issueStock)
        System.out.println("\n--> [2.4] Kiem tra xuat kho theo chien luoc FIFO (First-In First-Out):");

        // Buoc 1: Xuat 5 don vi P001. Lo dau tien B101 co 20 -> con 15. Lo B102 giu nguyen 30.
        System.out.println("--- Buoc 1: Xuat 5 don vi P001 (Tru bot tu lo dau B101)");
        boolean out1 = wm.issueStock("P001", 5, "Xuat ban le dot 1");
        assertTest("Xuat kho 5 don vi P001 thanh cong", out1, true);
        assertTest("Lo B101 con lai 15", p1Batches.peek().getQuantity() == 15, true);
        assertTest("Tong ton kho P001 con 45", pm.findProductByBarcode("P001").calculateTotalQuantity() == 45, true);

        // Buoc 2: Xuat tiep 25 don vi P001.
        // B101 co 15 -> vet sach 15 cua B101, B101 bi dequeue khoi hang doi!
        // 10 don vi con lai duoc tru tiep tu B102 (30 - 10 = 20).
        System.out.println("\n--- Buoc 2: Xuat 25 don vi P001 (Vet sach B101, sang B102 dung FIFO)");
        boolean out2 = wm.issueStock("P001", 25, "Xuat si cho dai ly");
        assertTest("Xuat kho 25 don vi P001 thanh cong", out2, true);
        assertTest("Lo B101 da het va bi dequeue, dau hang doi hien tai la B102",
                p1Batches.peek().getBatchId().equals("B102"), true);
        assertTest("Lo B102 con lai dung 20 don vi", p1Batches.peek().getQuantity() == 20, true);
        assertTest("Hang doi P001 chi con lai 1 lo hang", p1Batches.getSize() == 1, true);
        assertTest("Tong ton kho P001 con 20", pm.findProductByBarcode("P001").calculateTotalQuantity() == 20, true);

        // Buoc 3: Kiem tra khong du ton kho de xuat (Insufficient stock)
        System.out.println("\n--- Buoc 3: Kiem tra yeu cau xuat vuot qua so luong ton hien co");
        boolean outOver = wm.issueStock("P001", 100, "Thu xuat qua so luong");
        assertTest("Tu choi xuat kho khi ton kho khong du (Yeu cau 100, co 20)", outOver, false);
        assertTest("Ton kho P001 van duoc bao toan bang 20",
                pm.findProductByBarcode("P001").calculateTotalQuantity() == 20, true);

        // Buoc 4: Xuat voi tham so khong hop le
        assertTest("Tu choi xuat kho voi so luong <= 0", wm.issueStock("P001", 0, "SL 0"), false);
        assertTest("Tu choi xuat kho cho san pham khong ton tai", wm.issueStock("P999", 5, "Khong ton tai"), false);

        // 2.5 Kiem tra rang buoc khong duoc xoa san pham khi con ton kho
        System.out.println("\n--> [2.5] Kiem tra rang buoc nghiep vu: Khong the xoa san pham khi con ton kho:");
        assertTest("Tu choi xoa P001 vi van con 20 don vi ton kho", pm.deleteProduct("P001"), false);
        assertTest("Xac nhan P001 van con trong he thong", pm.hasProduct("P001"), true);

        // 2.6 Kiem tra xu ly lo hang het han (processExpiredBatches)
        System.out.println("\n--> [2.6] Kiem tra xu ly va loai bo lo hang het han (processExpiredBatches):");
        Product p4Before = pm.findProductByBarcode("P004");
        assertTest("P004 truoc khi huy lo co 10 goi het han", p4Before.calculateTotalQuantity() == 10, true);

        List<Batch> expiredList = wm.processExpiredBatches("P004", today);
        assertTest("Phat hien va tra ve danh sach lo het han cua P004",
                expiredList != null && expiredList.size() == 1 && expiredList.get(0).getBatchId().equals("B401"), true);
        assertTest("P004 sau khi xu ly het han co ton kho = 0", p4Before.calculateTotalQuantity() == 0, true);
        assertTest("Hang doi cua P004 hien da rong", p4Before.getBatchQueue().isEmpty(), true);

        // Sau khi P004 het sach hang (ton kho = 0), thu xoa P004
        assertTest("Xoa thanh cong P004 sau khi kho da sach ton kho", pm.deleteProduct("P004"), true);
        assertTest("Xac nhan P004 da bi xoa khoi he thong", pm.hasProduct("P004"), false);
    }

    // =========================================================================
    // PHAN 3: KIEM THU STATISTICS MANAGER (THONG KE & BAO CAO)
    // =========================================================================
    private static void testStatisticsManager(StatisticsManager sm, WarehouseManager wm, ProductManager pm) {
        printSectionHeader("PHAN 3: KIEM THU NGHIEP VU STATISTICS MANAGER");

        // 3.1 Bao cao tong hop ton kho
        System.out.println("--> [3.1] Bao cao tong hop ton kho (generateInventorySummary):");
        sm.generateInventorySummary();

        // 3.2 Kiem tra canh bao hang ton kho thap va lo sap het han
        System.out.println("--> [3.2] Kiem tra canh bao ton kho thap va lo sap het han:");
        sm.checkExpiredOrLowStockBatches();

        // 3.3 Kiem tra sap xep theo so luong bang QuickSort
        System.out.println("--> [3.3] Kiem tra QuickSort sap xep san pham theo so luong:");
        sm.displaySortedProductsByQuantity();

        // Kiem tra thuat toan getSortedProductsByQuantity (giam dan va tang dan)
        List<Product> descList = sm.getSortedProductsByQuantity(true);
        boolean isDescSorted = true;
        for (int i = 0; i < descList.size() - 1; i++) {
            if (descList.get(i).calculateTotalQuantity() < descList.get(i + 1).calculateTotalQuantity()) {
                isDescSorted = false;
                break;
            }
        }
        assertTest("Danh sach QuickSort giam dan dung thu tu", isDescSorted, true);

        List<Product> ascList = sm.getSortedProductsByQuantity(false);
        boolean isAscSorted = true;
        for (int i = 0; i < ascList.size() - 1; i++) {
            if (ascList.get(i).calculateTotalQuantity() > ascList.get(i + 1).calculateTotalQuantity()) {
                isAscSorted = false;
                break;
            }
        }
        assertTest("Danh sach QuickSort tang dan dung thu tu", isAscSorted, true);

        // 3.4 Bao cao lich su giao dich
        System.out.println("\n--> [3.4] Bao cao lich su giao dich theo tung ma san pham:");
        System.out.println("--- Bao cao giao dich cua P001 (Nhap 50, Xuat 30, Con 20):");
        sm.generateTransactionReport("P001");

        System.out.println("--- Bao cao toan bo giao dich kho (ALL):");
        sm.generateTransactionReport("ALL");
    }

    // =========================================================================
    // PHAN 4: KIEM THU DATA STRUCTURES & CAC HAM TINH TOAN
    // =========================================================================
    private static void testDataStructuresAndEdgeCases(ProductBST tree, TransactionLog log) {
        printSectionHeader("PHAN 4: KIEM THU CAU TRUC DU LIEU (BST, QUEUE, LOG)");

        // 4.1 Kiem tra tinh chat Binary Search Tree
        System.out.println("--> [4.1] Kiem tra tinh chat cay BST (In-order traversal sort theo Barcode):");
        List<Product> list = tree.inOrderList();
        boolean bstValid = true;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getBarcode().compareToIgnoreCase(list.get(i + 1).getBarcode()) > 0) {
                bstValid = false;
                break;
            }
        }
        assertTest("Duyet cay In-order cho danh sach barcode tang dan theo thu tu tu dien", bstValid, true);

        // 4.2 Kiem tra TransactionLog
        System.out.println("\n--> [4.2] Kiem tra TransactionLog ghi nhan va thong ke theo loai giao dich:");
        int inQtyP1 = log.calculateTotalByType("P001", TransactionType.INBOUND);
        int outQtyP1 = log.calculateTotalByType("P001", TransactionType.OUTBOUND);
        int expQtyP1 = log.calculateTotalByType("P001", TransactionType.EXPIRED);

        assertTest("Tong so luong nhap P001 ghi nhan = 50", inQtyP1 == 50, true);
        assertTest("Tong so luong xuat P001 ghi nhan = 30", outQtyP1 == 30, true);
        assertTest("Tong so luong het han P001 ghi nhan = 0", expQtyP1 == 0, true);

        // Giao dich het han cua P004 truoc khi bi xoa
        int expQtyP4 = log.calculateTotalByType("P004", TransactionType.EXPIRED);
        assertTest("Tong so luong het han P004 ghi nhan = 10", expQtyP4 == 10, true);

        List<StockTransaction> allTx = log.getAllTransactions();
        assertTest("TransactionLog chua it nhat 8 giao dich da thuc hien", allTx != null && allTx.size() >= 8, true);

        // 4.3 Kiem tra BatchQueue don le
        System.out.println("\n--> [4.3] Kiem tra cac thao tac co ban tren BatchQueue:");
        BatchQueue testQueue = new BatchQueue();
        assertTest("Queue moi khoi tao rong", testQueue.isEmpty(), true);
        assertTest("Queue peek() khi rong tra ve null", testQueue.peek() == null, true);
        assertTest("Queue dequeue() khi rong tra ve null", testQueue.dequeue() == null, true);

        Batch tb1 = new Batch("T1", "TEST", 10, 1000, LocalDate.now(), LocalDate.now().plusDays(10), LocalDate.now());
        Batch tb2 = new Batch("T2", "TEST", 20, 1000, LocalDate.now(), LocalDate.now().plusDays(20), LocalDate.now());
        testQueue.enqueue(tb1);
        testQueue.enqueue(tb2);

        assertTest("Queue co size = 2 sau 2 lan enqueue", testQueue.getSize() == 2, true);
        assertTest("Queue peek() tra ve phan tu dau tien (T1)", testQueue.peek().getBatchId().equals("T1"), true);
        assertTest("Tong so luong trong queue = 30", testQueue.getTotalQuantityInQueue() == 30, true);

        Batch dequeued = testQueue.dequeue();
        assertTest("Queue dequeue() lay dung T1", dequeued != null && dequeued.getBatchId().equals("T1"), true);
        assertTest("Queue size con 1", testQueue.getSize() == 1, true);
        assertTest("Queue peek() hien tai la T2", testQueue.peek().getBatchId().equals("T2"), true);

        testQueue.dequeue();
        assertTest("Queue rong sau khi dequeue het", testQueue.isEmpty(), true);
    }

    // =========================================================================
    // CAC HAM HO TRO TEST VA FORMAT IN AN
    // =========================================================================
    private static void assertTest(String testName, boolean condition, boolean expected) {
        totalTests++;
        boolean passed = (condition == expected);
        if (passed) {
            passedTests++;
            System.out.printf("  [PASS] %-70s -> OK%n", testName);
        } else {
            failedTests++;
            System.out.printf("  [FAIL] %-70s -> FAILED (Expected: %b, Got: %b)%n", testName, expected, condition);
        }
    }

    private static void printSectionHeader(String title) {
        System.out.println("\n=========================================================================================");
        System.out.println("  " + title);
        System.out.println("=========================================================================================");
    }

    private static void printTestSummary() {
        System.out.println("\n=========================================================================================");
        System.out.println("                               TEST EXECUTION SUMMARY                                    ");
        System.out.println("=========================================================================================");
        System.out.printf("  - Total Test Cases Executed : %d%n", totalTests);
        System.out.printf("  - Test Cases Passed         : %d (%.1f%%)%n", passedTests, (passedTests * 100.0 / totalTests));
        System.out.printf("  - Test Cases Failed         : %d%n", failedTests);
        System.out.println("-----------------------------------------------------------------------------------------");
        if (failedTests == 0) {
            System.out.println("  >>> ALL TESTS PASSED SUCCESSFULLY! SYSTEM LOGIC IS HEALTHY AND ACCURATE. <<<");
        } else {
            System.out.println("  >>> SOME TESTS FAILED! PLEASE CHECK THE LOGS ABOVE. <<<");
        }
        System.out.println("=========================================================================================\n");
    }
}
