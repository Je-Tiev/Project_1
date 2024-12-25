package com.example.clamav.service;

import com.example.clamav.model.ScanResult;
import java.io.*;
import java.net.Socket;

public class ClamAVService {
    private static final String HOST = "localhost";
    private static final int PORT = 3310;
    private static final int CHUNK_SIZE = 2048;

    public boolean testConnection() {
        // Code kiểm tra kết nối...
    }

    public ScanResult scanFile(File file) throws IOException {
        // Code quét file...
    }

    public void scanDirectory(File directory, Consumer<String> logger) {
        // Code quét thư mục...
    }

    public void updateDatabase() throws IOException {
        // Code cập nhật database...
    }
}package com.example.clamav.controller;

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