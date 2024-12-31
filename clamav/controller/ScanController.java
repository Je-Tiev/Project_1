package clamav.controller;

import clamav.model.ScanResult;
import clamav.service.ClamAVService;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ScanController {
    private final ClamAVService clamAVService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);


    public ScanController(ClamAVService clamAVService) {
        this.clamAVService = clamAVService;
    }

    public void scanFile(File file, Consumer<ScanResult> onResult, Consumer<String> onError) {
        executorService.submit(() -> {
            try {
                ScanResult result = clamAVService.scanFile(file);
                onResult.accept(result);
            } catch (Exception e) {
                onError.accept("Lỗi khi quét file: " + e.getMessage());
            }
        });
    }

    public void scanDirectory(File directory, Consumer<String> logger) {
        executorService.submit(() -> clamAVService.scanDirectory(directory, logger));
    }

    public void updateDatabase(Consumer<String> onSuccess, Consumer<String> onError) {
        executorService.submit(() -> {
            try {
                clamAVService.updateDatabase();
                onSuccess.accept("Cập nhật database thành công");
            } catch (Exception e) {
                onError.accept("Lỗi khi cập nhật database: " + e.getMessage());
            }
        });
    }

    public boolean testConnection() {
        return clamAVService.testConnection();
    }

    public void shutdown() {
        executorService.shutdown();
    }
}