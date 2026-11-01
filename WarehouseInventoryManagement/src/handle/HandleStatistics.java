package handle;

import controller.StatisticsManager;
import view.ConsoleView;

/**
 *
 * @author mac
 */
public class HandleStatistics {

    private ConsoleView view = new ConsoleView();
    private StatisticsManager statisticsManager = new StatisticsManager();

    public HandleStatistics() {
    }

    public HandleStatistics(StatisticsManager statisticsManager) {
        this.statisticsManager = statisticsManager;
    }

    // ==========================================
    // MENU 3: STATISTICS & REPORTS
    // ==========================================
    public void handleStatisticsMenu() {
        boolean back = false;
        while (!back) {
            int choice = view.showStatisticsSubMenu();
            switch (choice) {
                case 1:
                    statisticsManager.generateInventorySummary();
                    break;
                case 2:
                    statisticsManager.checkExpiredOrLowStockBatches();
                    break;
                case 3:
                    statisticsManager.displaySortedProductsByQuantity();
                    break;
                case 4: {
                    String barcode = view.inputBarcodePrompt("view history (enter ALL to view all)");
                    statisticsManager.generateTransactionReport(barcode);
                    break;
                }
                case 0:
                    back = true;
                    break;
            }
        }
    }
}
