package clamav.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {
    Runnable onScanFile;
    Runnable onScanFolder;
    Runnable onUpdateDb;
    Runnable onClearLog;
    Runnable onShowStatistics;
    Runnable onExportData;


    public ControlPanel(
            Runnable onScanFile,
            Runnable onScanFolder,
            Runnable onUpdateDb,
            Runnable onClearLog,
            Runnable onShowStatistics,
            Runnable onExportData
    ) {
        this.onScanFile = onScanFile;
        this.onScanFolder = onScanFolder;
        this.onUpdateDb = onUpdateDb;
        this.onClearLog = onClearLog;
        this.onShowStatistics = onShowStatistics;
        this.onExportData = onExportData;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setupButtons();
    }

    private void setupButtons() {
        // Code setup các buttons...
        JButton scanFileButton = new JButton("Quét File");
        scanFileButton.addActionListener(e -> onScanFile.run());
        add(scanFileButton);

        JButton scanFolderButton = new JButton("Quét Thư Mục");
        scanFolderButton.addActionListener(e -> onScanFolder.run());
        add(scanFolderButton);

        JButton updateDbButton = new JButton("Cập nhật Database");
        updateDbButton.addActionListener(e -> onUpdateDb.run());
        add(updateDbButton);

        JButton clearLogButton = new JButton("Xóa Log");
        clearLogButton.addActionListener(e -> onClearLog.run());
        add(clearLogButton);

        JButton statisticsButton = new JButton("Thống Kê");
        statisticsButton.addActionListener(e -> onShowStatistics.run());
        add(statisticsButton);

        JButton exportButton = new JButton("Xuất Dữ Liệu");
        exportButton.addActionListener(e -> onExportData.run());
        add(exportButton);
    }
}