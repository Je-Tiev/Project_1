package clamav.service;

import clamav.model.ScanResult;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService {
    private static final Logger logger = LoggerFactory.getLogger(ExportService.class);
    public void export(List<ScanResult> scanResults, String format) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Scan Results");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (format.equalsIgnoreCase("excel")) {
                exportToExcel(scanResults, fileToSave);
            } else if (format.equalsIgnoreCase("csv")) {
                exportToCSV(scanResults, fileToSave);
            } else {
                logger.error("Invalid export format: " + format);
            }
        }
    }

    private void exportToExcel(List<ScanResult> scanResults, File fileToSave) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Scan Results");
            createHeaderRow(sheet);
            int rowNum = 1;
            for (ScanResult result : scanResults) {
                Row row = sheet.createRow(rowNum++);
                createDataRow(result, row);
            }
            try (FileOutputStream fileOut = new FileOutputStream(fileToSave.getAbsolutePath() + ".xlsx")) {
                workbook.write(fileOut);
                logger.info("Exported data to excel: " + fileToSave.getAbsolutePath());
            }
        } catch (IOException e) {
            logger.error("Error exporting to excel", e);
        }
    }

    private void exportToCSV(List<ScanResult> scanResults, File fileToSave) {
        try (FileWriter fileWriter = new FileWriter(fileToSave.getAbsolutePath() + ".csv")) {
            fileWriter.append("Clean,Message,FileName,FileSize,ScanTime,ScanType,Status\n");
            for (ScanResult result : scanResults) {
                fileWriter.append(String.valueOf(result.isClean())).append(",");
                fileWriter.append(result.getMessage().replace(",", ";")).append(",");
                fileWriter.append(result.getFileName().replace(",", ";")).append(",");
                fileWriter.append(String.valueOf(result.getFileSize())).append(",");
                fileWriter.append(result.getScanTime()).append(",");
                fileWriter.append(result.getScanType()).append(",");
                fileWriter.append(result.getStatus()).append("\n");
            }
            logger.info("Exported data to CSV: " + fileToSave.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error exporting to CSV", e);
        }
    }
    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Clean");
        headerRow.createCell(1).setCellValue("Message");
        headerRow.createCell(2).setCellValue("File Name");
        headerRow.createCell(3).setCellValue("File Size");
        headerRow.createCell(4).setCellValue("Scan Time");
        headerRow.createCell(5).setCellValue("Scan Type");
        headerRow.createCell(6).setCellValue("Status");
    }

    private void createDataRow(ScanResult result, Row row) {
        row.createCell(0).setCellValue(result.isClean());
        row.createCell(1).setCellValue(result.getMessage());
        row.createCell(2).setCellValue(result.getFileName());
        row.createCell(3).setCellValue(result.getFileSize());
        row.createCell(4).setCellValue(result.getScanTime());
        row.createCell(5).setCellValue(result.getScanType());
        row.createCell(6).setCellValue(result.getStatus());
    }
}