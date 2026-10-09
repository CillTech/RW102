package frontend;

import backend.controller.DepartmentController;
import entity.Department;

import java.util.List;

public class DepartmentFunction extends Function {
    private DepartmentController depController = new DepartmentController();

    public void menuDepartment() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ DEPARTMENT ---");
            System.out.println("1. Hiển thị toàn bộ department");
            System.out.println("2. Tìm kiếm department theo tên");
            System.out.println("3. Thêm mới department");
            System.out.println("4. Xóa department theo ID");
            System.out.println("5. Update tên phòng ban theo ID");
            System.out.println("6. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-6): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hienThiDepartment(); break;
                case "2": timKiemDepartment(); break;
                case "3": themMoiDepartment(); break;
                case "4": xoaDepartment(); break;
                case "5": capNhatDepartment(); break;
                case "6": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
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
        String name = nhapChuoiKhongRong("Nhập tên phòng ban mới: ");
        if (depController.themMoi(name)) System.out.println("Thêm thành công!");
        else System.out.println("Thêm thất bại (Có thể trùng tên)!");
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

    public int nhapIdDepartmentTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (depController.isDepartmentIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Phòng ban không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
    }

    public void inBangDepartment(List<Department> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("+----+-------------------------+");
        System.out.printf("| %-2s | %-23s |\n", "ID", "Department Name");
        System.out.println("+----+-------------------------+");
        for (Department d : list) {
            System.out.printf("| %-2d | %-23s |\n", d.getId(), d.getName());
        }
        System.out.println("+----+-------------------------+");
    }
}