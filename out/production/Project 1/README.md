# ClamAV Scanner

## Giới thiệu

Ứng dụng này là một công cụ quét virus sử dụng ClamAV, một engine quét virus mã nguồn mở, để quét file và thư mục trên hệ thống. Ứng dụng cung cấp một giao diện người dùng đồ họa (GUI) đơn giản, dễ sử dụng, cho phép quét các file và thư mục, cập nhật cơ sở dữ liệu virus, và theo dõi tiến trình quét.

## Tính năng

*   **Quét file:** Chọn và quét từng file riêng lẻ.
*   **Quét thư mục:** Quét toàn bộ nội dung của một thư mục.
*   **Cập nhật database:** Cập nhật cơ sở dữ liệu virus của ClamAV để đảm bảo hiệu quả quét tốt nhất.
*   **Kiểm tra kết nối:** Kiểm tra kết nối tới ClamAV daemon khi khởi động.
*   **Hiển thị trạng thái:** Cập nhật trạng thái, tiến trình, và kết quả quét trong thời gian thực.
*   **Ghi log:** Ghi lại các hoạt động quét và lỗi vào log panel.
*   **Giao diện trực quan:** Sử dụng thư viện Swing để tạo giao diện người dùng đơn giản và dễ sử dụng.

## Cài đặt

### Yêu cầu

*   **Java:** Cài đặt Java Development Kit (JDK) phiên bản 8 trở lên.
*   **ClamAV:** Cài đặt ClamAV và ClamAV daemon (clamd) trên hệ thống. Đảm bảo rằng `clamd` đang chạy và lắng nghe trên cổng 3310 (mặc định).

### Các bước cài đặt

1.  **Tải source code:** Tải source code của dự án từ repository.
2.  **Build dự án:** Sử dụng IDE (IntelliJ IDEA, Eclipse,...) hoặc dòng lệnh để build dự án (ví dụ với Maven):

    ```bash
    mvn clean install
    ```

    hoặc (nếu dùng Gradle)

    ```bash
    gradle build
    ```
3.  **Chạy ứng dụng:** Sử dụng lệnh sau để chạy ứng dụng:

    ```bash
    java -jar <path-to-your-jar-file>
    ```
    hoặc chạy trực tiếp từ IDE

## Hướng dẫn sử dụng

1.  **Khởi động:** Chạy ứng dụng `ClamAV Scanner`.
2.  **Kết nối:** Ứng dụng sẽ tự động kiểm tra kết nối tới ClamAV daemon. Nếu kết nối thành công, sẽ thấy thông báo "Đã kết nối với ClamAV" trên thanh trạng thái.
3.  **Quét File:**
  *   Chọn nút "Quét File".
  *   Chọn file muốn quét.
  *   Kết quả quét sẽ được hiển thị trên panel log và thanh trạng thái.
4.  **Quét Thư mục:**
  *   Chọn nút "Quét Thư mục".
  *   Chọn thư mục muốn quét.
  *   Tiến trình quét và kết quả sẽ được hiển thị trên panel log và thanh trạng thái.
5.  **Cập nhật Database:**
  *   Chọn nút "Cập nhật Database".
  *   Sau khi quá trình cập nhật hoàn tất. Thông báo thành công hoặc lỗi trên thanh trạng thái sẽ xuất hiện.
6.  **Xóa Log:**
  *    Chọn nút "Xóa Log" để xóa toàn bộ log.
7.  **Đóng ứng dụng:** Nhấn vào nút đóng cửa sổ để tắt ứng dụng.

## Công nghệ sử dụng

*   **Java:** Ngôn ngữ lập trình chính.
*   **Swing:** Thư viện để tạo giao diện người dùng đồ họa.
*   **ClamAV:** Engine quét virus.
*   **Maven/Gradle:** Build tool để quản lý các dependency.

## Các điểm cần lưu ý

*   Ứng dụng này yêu cầu ClamAV daemon đang chạy trên hệ thống.
*   Đảm bảo rằng ClamAV daemon đang lắng nghe trên cổng 3310 (hoặc thay đổi port trong code).
*   Các tác vụ quét và cập nhật có thể mất một khoảng thời gian, vui lòng kiên nhẫn.

---