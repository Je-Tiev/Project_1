# ClamAV Scanner

## Tổng quan

Dự án sử dụng công cụ quét virus ClamAV, một công cụ quét virus mã nguồn mở, để quét file và thư mục trên hệ thống. Cung cấp giao diện người dùng đồ họa đơn giản, dễ sử dụng, cho phép quét các file và thư mục, cập nhật cơ sở dữ liệu virus, thống kê và phân tích kết quả thực hiện quét, sinh biểu đồ cột và xuất kết quả dạng tệp excel, csv.

## Tính năng

*   **Quét file:** Chọn và quét từng file riêng lẻ.
*   **Quét thư mục:** Quét toàn bộ nội dung của một thư mục.
*   **Cập nhật database:** Cập nhật cơ sở dữ liệu virus của ClamAV.
*   **Kiểm tra kết nối:** Kiểm tra kết nối tới ClamAV daemon.
*   **Hiển thị trạng thái:** Cập nhật trạng thái, tiến trình, và kết quả quét trong thời gian thực.
*   **Ghi log:** Ghi lại các hoạt động quét và lỗi vào log panel.
*   **Thống kê và sinh biểu đồ cột:** Tổng file đã quét, file sạch, file nhiễm virus và lỗi khi quét.
*   **Xuất kết quả:** Định dạng Excel và csv.  

## Hướng dẫn sử dụng

1.  **Khởi động:** Khởi động `ClamAV Scanner`.
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

## Các điểm cần lưu ý

*   Ứng dụng này yêu cầu ClamAV daemon đang chạy trên hệ thống.
*   Đảm bảo rằng ClamAV daemon đang lắng nghe trên cổng 3310 (hoặc thay đổi port trong code).
*   Các tác vụ quét và cập nhật có thể mất một khoảng thời gian, vui lòng kiên nhẫn.

---
