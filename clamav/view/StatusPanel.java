package com.example.clamav.view;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    private final JLabel statusLabel;
    private final JProgressBar progressBar;

    public StatusPanel() {
        setLayout(new BorderLayout(5, 5));
        statusLabel = new JLabel("Sẵn sàng");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        add(statusLabel, BorderLayout.WEST);
        add(progressBar, BorderLayout.CENTER);
    }

    public void updateStatus(String status, int progress) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
            progressBar.setValue(progress);
        });
    }
}