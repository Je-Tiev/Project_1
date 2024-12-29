package com.example.clamav.model;

public class ScanResult {
    private final boolean clean;
    private final String message;
    private final String fileName;
    private final long fileSize;
    private final String scanTime;

    public ScanResult(boolean clean, String message, String fileName, long fileSize) {
        this.clean = clean;
        this.message = message;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.scanTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public boolean isClean() {
        return clean;
    }

    public String getMessage() {
        return message;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getScanTime() {
        return scanTime;
    }

    @Override
    public String toString() {
        return "ScanResult{" +
                "clean=" + clean +
                ", message='" + message + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", scanTime='" + scanTime + '\'' +
                '}';
    }
}