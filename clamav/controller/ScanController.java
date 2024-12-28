package com.example.clamav.controller;

import com.example.clamav.model.ScanResult;
import com.example.clamav.service.ClamAVService;
import java.io.File;
import java.util.function.Consumer;

public class ScanController {
    private final ClamAVService clamAVService;

    public ScanController(ClamAVService clamAVService) {
        this.clamAVService = clamAVService;
    }

    public void scanFile(File file, Consumer<ScanResult> onResult, Consumer<String> onError) {
        try {
            ScanResult result = clamAVService.scanFile(file);
            onResult.accept(result);
        } catch (Exception e) {
            onError.accept("Lỗi khi quét file: " + e.getMessage());
        }
    }

    public void scanDirectory(File directory, Consumer<String> logger) {
        clamAVService.scanDirectory(directory, logger);
    }

    public void updateDatabase(Consumer<String> onSuccess, Consumer<String> onError) {
        try {
            clamAVService.updateDatabase();
            onSuccess.accept("Cập nhật database thành công");
        } catch (Exception e) {
            onError.accept("Lỗi khi cập nhật database: " + e.getMessage());
        }
    }

    public boolean testConnection() {
        return clamAVService.testConnection();
    }
}