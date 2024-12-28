# Project_1

# Triển khai API đơn giản để tương tác với ClamAV sử dụng Spring Boot

## Các thành phần chính

### 1. **ClamAVService**
Lớp chính xử lý việc kết nối và giao tiếp với ClamAV daemon.  
- **Chức năng:**
  - Kiểm Tra Kết Nối đến ClamAV Server (testConnection()).
  -   Kiểm tra xem có kết nối thành công đến ClamAV server hay không.
  - Quét Một File (scanFile(File file)).
  -   Quét một file cụ thể để phát hiện xem file có bị nhiễm virus hay không.
  - Quét Thư Mục (scanDirectory(File directory, Consumer<String> logger)).
  -   Quét toàn bộ các file trong thư mục "File directory"(bao gồm cả các thư mục con).
  - Cập Nhật Database Virus (updateDatabase()):
  -   Yêu cầu ClamAV server cập nhật database virus.

---

### 2. **ScanResult**
Lớp đại diện cho kết quả quét.  
- **Chứa:**
  - **Trạng thái**: Clean (sạch) hoặc Infected (nhiễm).
  - **Thông báo chi tiết** về kết quả quét.

---

### 3. **ClamAVController**
REST Controller để tiếp nhận requests từ người dùng.  
- **Endpoint chính:**
  - **POST `/api/scan/file`**: Tiếp nhận file để quét, xử lý lỗi, và trả về kết quả phù hợp.

---

## Cách cài đặt và sử dụng

1. **Cài đặt ClamAV daemon** và đảm bảo nó đang chạy.
2. **Cấu hình host và port** của ClamAV daemon trong file `application.properties`:
   ```properties
   clamav.host=localhost
   clamav.port=3310
