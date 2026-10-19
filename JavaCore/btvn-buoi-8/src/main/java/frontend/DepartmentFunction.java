package frontend;

import entity.Department;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DepartmentFunction extends Function {

    public void menuDepartment() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ DEPARTMENT ---");
            System.out.println("1. Hiển thị toàn bộ department");
            System.out.println("2. Tìm kiếm department theo tên");
            System.out.println("3. Thêm mới department");
            System.out.println("4. Xóa department theo ID");
            System.out.println("5. Update tên phòng ban theo ID");
            System.out.println("6. Import department từ file CSV");
            System.out.println("7. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-7): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hienThiDepartment(); break;
                case "2": timKiemDepartment(); break;
                case "3": themMoiDepartment(); break;
                case "4": xoaDepartment(); break;
                case "5": capNhatDepartment(); break;
                case "6": importDepartmentFromCSV(); break;
                case "7": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void importDepartmentFromCSV() {
        System.out.print("Nhập đường dẫn file CSV (mặc định 'department.csv'): ");
        String filePath = sc.nextLine().trim();
        if (filePath.isEmpty()) {
            filePath = "department.csv";
        }

        List<String> validDepartments = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
             BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || (lineNumber == 1 && line.toLowerCase().startsWith("department_name"))) {
                    continue;
                }

                String depName = line.split(",")[0].trim();
                if (depController.isDepartmentNameTonTai(depName)) {
                    System.out.println("[-] Dòng " + lineNumber + ": Phòng ban '" + depName + "' đã tồn tại -> Bỏ qua.");
                    continue;
                }

                validDepartments.add(depName);
            }

            if (!validDepartments.isEmpty()) {
                int inserted = depController.themDanhSach(validDepartments);
                System.out.println("\n>> Đã lưu thành công " + inserted + "/" + validDepartments.size() + " department vào cơ sở dữ liệu.");
            } else {
                System.out.println("\n>> Không có dữ liệu hợp lệ để lưu vào DB.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }
    }

    public void hienThiDepartment() {
        System.out.println("==== DANH SÁCH DEPARTMENT ====");
        inBangDepartment(depController.layTatCa());
    }

    private void timKiemDepartment() {
        String name = nhapChuoiKhongRong("Nhập tên phòng ban cần tìm: ");
        List<Department> kq = depController.timKiemTheoTen(name);
        if (kq.isEmpty()) System.out.println("Không tìm thấy kết quả nào!");
        else inBangDepartment(kq);
    }

    private void themMoiDepartment() {
        String name;
        while (true) {
            name = nhapChuoiKhongRong("Nhập tên phòng ban mới: ");
            if (depController.isDepartmentNameTonTai(name)) {
                System.out.println("Lỗi: Tên phòng ban đã tồn tại trong DB, vui lòng nhập lại!");
            } else {
                break;
            }
        }

        if (depController.themMoi(name)) System.out.println("Thêm thành công!");
        else System.out.println("Thêm thất bại!");
    }

    private void xoaDepartment() {
        hienThiDepartment();
        int id = nhapIdDepartmentTonTai("\nNhập ID phòng ban cần xóa: ");
        if (depController.xoaTheoId(id)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại (Phòng ban đang có nhân viên phụ thuộc)!");
        }
    }

    private void capNhatDepartment() {
        hienThiDepartment();
        int id = nhapIdDepartmentTonTai("\nNhập ID phòng ban cần sửa: ");
        String newName = nhapChuoiKhongRong("Nhập tên phòng ban mới: ");

        if (depController.capNhatTenPhongBan(id, newName)) System.out.println("Cập nhật thành công!");
        else System.out.println("Cập nhật thất bại!");
    }
}