package clamav.service;

import clamav.model.ScanResult;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.function.Consumer;

public class ClamAVService {
    private static final String HOST = "localhost";
    private static final int PORT = 3310;
    private static final int CHUNK_SIZE = 2048;

    public boolean testConnection() {
        try (Socket socket = new Socket(HOST, PORT)) {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("PING\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            Scanner scanner = new Scanner(socket.getInputStream());
            if (scanner.hasNextLine()) {
                String response = scanner.nextLine();
                return "PONG".equals(response);
            }
        } catch (IOException e) {
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return false;
        }
        return false;
    }

    public ScanResult scanFile(File file) throws IOException {
        try (Socket socket = new Socket(HOST, PORT);
             OutputStream outputStream = socket.getOutputStream();
             FileInputStream fileInputStream = new FileInputStream(file)) {

            // Send INSTREAM command
            outputStream.write("INSTREAM\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            Scanner scanner = new Scanner(socket.getInputStream());
            String response = scanner.nextLine();
            if (!"OK".equals(response)) {
                throw new IOException("ClamAV không sẵn sàng: " + response);
            }


            long fileSize = file.length();
            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                // Send size of chunk
                String size = String.valueOf(bytesRead);
                String paddedSize = String.format("%08x", Integer.parseInt(size));
                outputStream.write(paddedSize.getBytes(StandardCharsets.UTF_8));
                outputStream.write(buffer, 0, bytesRead);
                outputStream.flush();
            }

            // Send zero-sized chunk to indicate end
            outputStream.write("00000000".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            String scanResult = scanner.nextLine();
            boolean clean = scanResult.contains("OK");
            String message = scanResult;
            String status = clean ? "CLEAN" : "INFECTED"; // Set the default status
            if(scanResult.contains("FOUND")) {
                message = "Virus found: " + scanResult;
            }


            return new ScanResult(clean, message, file.getName(), fileSize, "FILE", status);

        } catch(IOException e) {
            System.err.println("Lỗi quét file " + file.getAbsolutePath() + " : " + e.getMessage());
            throw e;
        }
    }

    public void scanDirectory(File directory, Consumer<String> logger) {
        File[] files = directory.listFiles();
        if (files == null) {
            logger.accept("Không thể đọc thư mục: " + directory.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                scanDirectory(file, logger); // Recursively scan subdirectories
            } else {
                try {
                    ScanResult result = scanFile(file);
                    logger.accept("Quét file: " + file.getAbsolutePath() + ", Kết quả: " + result.getMessage());

                } catch (IOException e) {
                    logger.accept("Lỗi khi quét file " + file.getAbsolutePath() + ": " + e.getMessage());
                }
            }
        }
    }

    public void updateDatabase() throws IOException {
        try(Socket socket = new Socket(HOST, PORT);
            OutputStream outputStream = socket.getOutputStream();
            Scanner scanner = new Scanner(socket.getInputStream())){

            outputStream.write("RELOAD\n".getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            String response = scanner.nextLine();
            if (!"RELOADING".equals(response)) {
                throw new IOException("Lỗi khi cập nhật database: " + response);
            }
            System.out.println("Response update database: " + response);

            String endResponse = scanner.nextLine();
            if(!endResponse.contains("END")){
                throw new IOException("Lỗi khi cập nhật database: " + endResponse);
            }

        } catch (IOException e) {
            System.err.println("Lỗi khi cập nhật database: " + e.getMessage());
            throw e;
        }
    }
}