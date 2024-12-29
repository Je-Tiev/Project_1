package com.example.clamav.view;

import javax.swing.*;
import java.awt.*;

public class LogPanel extends JPanel {
    private final JTextArea logArea;

    public LogPanel() {
        setLayout(new BorderLayout());
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    public void clear() {
        SwingUtilities.invokeLater(() -> logArea.setText(""));

    }
}