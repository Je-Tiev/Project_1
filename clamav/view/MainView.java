package clamav.view;


import clamav.controller.ScanController;
import clamav.model.ScanResult;
import clamav.service.ChartGenerator;
import clamav.service.ExportService;
import clamav.service.StatisticsService;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileSystemView;


public class MainView extends JFrame {
    private final ScanController controller;
    private final LogPanel logPanel;
    private final StatusPanel statusPanel;
    private final ControlPanel controlPanel;
    private final List<ScanResult> scanResults = new ArrayList<>(); // Lưu trữ các kết quả scan
    private final StatisticsService statisticsService = new StatisticsService();
    private final ChartGenerator chartGenerator = new ChartGenerator();
    private final ExportService exportService = new ExportService();




    public MainView(ScanController controller) {
        this.controller = controller;
        setTitle("ClamAV Scanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));




        // Khởi tạo components
        logPanel = new LogPanel();
        statusPanel = new StatusPanel();
        controlPanel = new ControlPanel(
                this::handleScanFile,
                this::handleScanFolder,
                this::handleUpdateDatabase,
                logPanel::clear,
                this::showStatistics,
                this::exportData
        );


        // Setup UI
        setupUI();


        // Kiểm tra kết nối
        checkConnection();
    }


    private void setupUI() {
        add(logPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.NORTH);
    }


    private void checkConnection() {
        statusPanel.updateStatus("Đang kiểm tra kết nối...", 0);
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return controller.testConnection();
            }


            @Override
            protected void done() {
                try {
                    boolean isConnected = get();
                    if (isConnected) {
                        statusPanel.updateStatus("Đã kết nối với ClamAV", 100);
                        logPanel.log("Đã kết nối với ClamAV");
                    } else {
                        statusPanel.updateStatus("Không thể kết nối ClamAV", 0);
                        logPanel.log("Không thể kết nối ClamAV, hãy kiểm tra lại ClamAV daemon");
                    }
                } catch (Exception e) {
                    statusPanel.updateStatus("Lỗi kết nối ClamAV", 0);
                    logPanel.log("Lỗi kết nối ClamAV: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }


    private void handleScanFile() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            statusPanel.updateStatus("Đang quét file: " + selectedFile.getName(), 0);
            logPanel.log("Bắt đầu quét file: " + selectedFile.getAbsolutePath());
            controller.scanFile(selectedFile, result -> onScanFileResult(result, "FILE"), this::onScanFileError);
        }
    }

    private void handleScanFolder() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            statusPanel.updateStatus("Đang quét thư mục: " + selectedDirectory.getName(), 0);
            logPanel.log("Bắt đầu quét thư mục: " + selectedDirectory.getAbsolutePath());
            controller.scanDirectory(selectedDirectory, message -> onScanFolderResult(message, "DIRECTORY"));
        }
    }


    private void handleUpdateDatabase() {
        statusPanel.updateStatus("Đang cập nhật database...", 0);
        logPanel.log("Bắt đầu cập nhật database");
        controller.updateDatabase(
                message -> {
                    statusPanel.updateStatus("Cập nhật database thành công", 100);
                    logPanel.log(message);
                },
                message -> {
                    statusPanel.updateStatus("Lỗi cập nhật database", 0);
                    logPanel.log(message);
                }
        );
    }


    private void onScanFileResult(ScanResult result, String type) {
        String status = result.isClean() ? "CLEAN" : "INFECTED";
        if(result.getMessage().contains("Lỗi")) {
            status = "ERROR";
        }
        ScanResult newResult = new ScanResult(result.isClean(), result.getMessage(), result.getFileName(), result.getFileSize(), type, status);
        statusPanel.updateStatus("Kết quả: " + newResult.getMessage(), 100);
        logPanel.log("Kết quả quét: " + newResult);
        scanResults.add(newResult);
    }

    private void onScanFolderResult(String message, String type) {
        String status = "CLEAN";
        if(message.contains("Virus found")) {
            status = "INFECTED";
        }
        if(message.contains("Lỗi")) {
            status = "ERROR";
        }
        String fileName = message.contains(":") ? message.split(":")[1].split(",")[0].trim() : "unknown";
        ScanResult newResult = new ScanResult(message.contains("OK"), message, fileName, 0, type, status);
        logPanel.log(message);
        scanResults.add(newResult);
    }


    private void onScanFileError(String error) {
        statusPanel.updateStatus("Lỗi quét file", 0);
        logPanel.log(error);
        scanResults.add(new ScanResult(false, error, "unknown", 0, "FILE", "ERROR")); // Add error result
    }
    private void showStatistics() {
        var statistics = statisticsService.calculateStatistics(scanResults);
        JFreeChart chart = chartGenerator.createChart(statistics);
        //JFreeChart chart = chartGenerator.createPieChart(statistics); // Uncomment this line if you want a pie chart instead

        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame chartFrame = new JFrame("Statistics Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.add(chartPanel);
        chartFrame.pack();
        chartFrame.setVisible(true);
    }
    private void exportData() {
        String[] options = {"Excel", "CSV"};
        int choice = JOptionPane.showOptionDialog(null, "Select export format", "Export Data", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            exportService.export(scanResults, "excel");
        }
        if (choice == 1){
            exportService.export(scanResults, "csv");
        }

    }

    @Override
    public void dispose() {
        controller.shutdown();
        super.dispose();
    }
}