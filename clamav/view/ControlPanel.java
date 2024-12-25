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
    }
}