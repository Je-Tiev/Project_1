package com.example.clamav.view;

import com.example.clamav.controller.ScanController;
import com.example.clamav.model.ScanResult;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileSystemView;

public class MainView extends JFrame {
    private final ScanController controller;
    private final LogPanel logPanel;
    private final StatusPanel statusPanel;
    private final ControlPanel controlPanel;


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
                logPanel::clear
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
            controller.scanFile(selectedFile, this::onScanFileResult, this::onScanFileError);

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
            controller.scanDirectory(selectedDirectory, logPanel::log);

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

    private void onScanFileResult(ScanResult result) {
        statusPanel.updateStatus("Kết quả: " + result.getMessage(), 100);
        logPanel.log("Kết quả quét: " + result);

    }

    private void onScanFileError(String error) {
        statusPanel.updateStatus("Lỗi quét file", 0);
        logPanel.log(error);
    }

    @Override
    public void dispose() {
        controller.shutdown();
        super.dispose();
    }
}