package com.example.clamav;

import com.example.clamav.view.MainView;
import com.example.clamav.controller.ScanController;
import com.example.clamav.service.ClamAVService;

public class Main {
    public static void main(String[] args) {
        try {
            // Set Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khởi tạo các components
        ClamAVService clamAVService = new ClamAVService();
        ScanController controller = new ScanController(clamAVService);

        // Khởi tạo và hiển thị GUI
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView(controller);
            mainView.setVisible(true);
        });
    }
}
