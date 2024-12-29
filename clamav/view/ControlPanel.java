package com.example.clamav.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ControlPanel extends JPanel {
    private final Consumer<Void> onScanFile;
    private final Consumer<Void> onScanFolder;
    private final Consumer<Void> onUpdateDb;
    private final Consumer<Void> onClearLog;

    public ControlPanel(
            Consumer<Void> onScanFile,
            Consumer<Void> onScanFolder,
            Consumer<Void> onUpdateDb,
            Consumer<Void> onClearLog) {

        this.onScanFile = onScanFile;
        this.onScanFolder = onScanFolder;
        this.onUpdateDb = onUpdateDb;
        this.onClearLog = onClearLog;

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
    }
}