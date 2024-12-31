package clamav.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {
    private final Consumer<Void> onScanFile;
    private final Consumer<Void> onScanFolder;
    private final Consumer<Void> onUpdateDb;
    private final Consumer<Void> onClearLog;
    private final Consumer<Void> onShowStatistics;
    private final Consumer<Void> onExportData;


    public ControlPanel(
            Consumer<Void> onScanFile,
            Consumer<Void> onScanFolder,
            Consumer<Void> onUpdateDb,
            Consumer<Void> onClearLog,
            Consumer<Void> onShowStatistics,
            Consumer<Void> onExportData
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
        scanFileButton.addActionListener(e -> onScanFile.accept(null));
        add(scanFileButton);

        JButton scanFolderButton = new JButton("Quét Thư Mục");
        scanFolderButton.addActionListener(e -> onScanFolder.accept(null));
        add(scanFolderButton);

        JButton updateDbButton = new JButton("Cập nhật Database");
        updateDbButton.addActionListener(e -> onUpdateDb.accept(null));
        add(updateDbButton);

        JButton clearLogButton = new JButton("Xóa Log");
        clearLogButton.addActionListener(e -> onClearLog.accept(null));
        add(clearLogButton);

        JButton statisticsButton = new JButton("Thống Kê");
        statisticsButton.addActionListener(e -> onShowStatistics.accept(null));
        add(statisticsButton);

        JButton exportButton = new JButton("Xuất Dữ Liệu");
        exportButton.addActionListener(e -> onExportData.accept(null));
        add(exportButton);
    }
}