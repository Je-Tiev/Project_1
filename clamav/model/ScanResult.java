package clamav.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScanResult {
    private final boolean clean;
    private final String message;
    private final String fileName;
    private final long fileSize;
    private final String scanTime;
    private final String scanType; // "FILE", "DIRECTORY"
    private final String status;  // "CLEAN", "INFECTED", "ERROR", "ERROR"


    public ScanResult(boolean clean, String message, String fileName, long fileSize, String scanType, String status) {
        this.clean = clean;
        this.message = message;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.scanTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.scanType = scanType;
        this.status = status;
    }

    public boolean isClean() {return clean;}

    public String getMessage() {return message;}

    public String getFileName() {return fileName;}

    public long getFileSize() {return fileSize;}

    public String getScanTime() {return scanTime;}

    public String getScanType() {return scanType;}

    public String getStatus() {return status;}

    @Override
    public String toString() {
        return "ScanResult{" +
                "clean=" + clean +
                ", message='" + message + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", scanTime='" + scanTime + '\'' +
                ", scanType='" + scanType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}