package frontend;

import backend.controller.AccountController;
import backend.controller.DepartmentController;
import entity.Account;
import entity.Department;

import java.util.List;
import java.util.Scanner;

public class Function {
    private AccountController accController = new AccountController();
    private DepartmentController depController = new DepartmentController();
    private Scanner sc = new Scanner(System.in);

    public void menu() {
        while (true) {
            System.out.println("\n========= PHẦN MỀM QUẢN LÝ =========");
            System.out.println("1. Quản lý Account");
            System.out.println("2. Quản lý Department");
            System.out.println("3. Thoát chương trình");
            System.out.print("Mời bạn chọn menu (1-3): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": menuAccount(); break;
                case "2": menuDepartment(); break;
                case "3":
                    System.out.println("Đã thoát chương trình thành công!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    // ---------------- MENU QUẢN LÝ ACCOUNT ----------------
    private void menuAccount() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ACCOUNT ---");
            System.out.println("1. Hiển thị toàn bộ account");
            System.out.println("2. Tìm kiếm account theo username");
            System.out.println("3. Thêm mới account");
            System.out.println("4. Xóa account theo username");
            System.out.println("5. Update fullname theo username");
            System.out.println("6. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-6): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hienThiAccount(); break;
                case "2": timKiemAccount(); break;
                case "3": themMoiAccount(); break;
                case "4": xoaAccount(); break;
                case "5": capNhatAccount(); break;
                case "6": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void hienThiAccount() {
        System.out.println("==== DANH SÁCH ACCOUNT ====");
        inBangAccount(accController.layTatCa());
    }

    private void timKiemAccount() {
        String username = nhapChuoiKhongRong("Nhập username cần tìm: ");
        List<Account> kq = accController.timKiemTheoUsername(username);
        if (kq.isEmpty()) System.out.println("Không tìm thấy kết quả nào!");
        else inBangAccount(kq);
    }

    private void themMoiAccount() {
        System.out.println("==== THÊM MỚI ACCOUNT ====");
        String email = nhapChuoiKhongRong("Nhập Email: ");
        String username = nhapChuoiKhongRong("Nhập Username: ");
        String fullName = nhapChuoiKhongRong("Nhập Full Name: ");

        System.out.println("\n--- Danh sách Phòng ban hiện có ---");
        List<Department> dsDep = depController.layTatCa();
        inBangDepartment(dsDep);

        int depId;
        while (true) {
            depId = nhapSoNguyenDuong("Mời bạn nhập ID Phòng ban (chọn số từ danh sách trên): ");

            boolean idTonTai = false;
            for (Department d : dsDep) {
                if (d.getId() == depId) {
                    idTonTai = true;
                    break;
                }
            }

            if (idTonTai) {
                break;
            } else {
                System.out.println("Lỗi: ID Phòng ban không tồn tại!");
            }
        }

        System.out.println("\n--- Danh sách Vị trí ---");
        System.out.println("1. Dev | 2. Test | 3. Scrum Master | 4. PM");

        int posId;
        while (true) {
            posId = nhapSoNguyenDuong("Mời bạn nhập ID Vị trí (1-4): ");
            if (posId >= 1 && posId <= 4) {
                break;
            } else {
                System.out.println("Lỗi: Vị trí không hợp lệ!");
            }
        }

        if (accController.themMoi(email, username, fullName, depId, posId)) {
            System.out.println("Thêm Account thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    private void xoaAccount() {
        hienThiAccount();
        String username = nhapChuoiKhongRong("\nNhập username cần xóa: ");
        if (accController.xoaTheoUsername(username)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Không tìm thấy account hoặc lỗi khi xóa.");
        }
    }

    private void capNhatAccount() {
        hienThiAccount();
        String username = nhapChuoiKhongRong("\nNhập username cần cập nhật: ");
        String newName = nhapChuoiKhongRong("Nhập Full Name mới: ");

        if (accController.capNhatFullName(username, newName)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    // ---------------- MENU QUẢN LÝ DEPARTMENT ----------------
    private void menuDepartment() {
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

    private void hienThiDepartment() {
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
        int id = nhapSoNguyenDuong("\nNhập ID phòng ban cần xóa: ");
        if (depController.xoaTheoId(id)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại (ID không tồn tại hoặc phòng ban đang có nhân viên)!");
        }
    }

    private void capNhatDepartment() {
        hienThiDepartment();
        int id = nhapSoNguyenDuong("\nNhập ID phòng ban cần sửa: ");
        String newName = nhapChuoiKhongRong("Nhập tên phòng ban mới: ");

        if (depController.capNhatTenPhongBan(id, newName)) System.out.println("Cập nhật thành công!");
        else System.out.println("Cập nhật thất bại (ID không tồn tại)!");
    }

    // ---------------- HÀM IN VÀ VALIDATION ----------------

    private void inBangAccount(List<Account> list) {
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        System.out.printf("| %-2s | %-25s | %-15s | %-18s | %-23s | %-18s |\n", "ID", "Email", "Username", "Full Name", "Department", "Position");
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        for (Account a : list) {
            String depName = (a.getDepartment().getName() != null) ? a.getDepartment().getName() : "Chưa có";
            String posName = (a.getPosition().getName() != null) ? a.getPosition().getName() : "Chưa có";
            System.out.printf("| %-2d | %-25s | %-15s | %-18s | %-23s | %-18s |\n",
                    a.getId(), a.getEmail(), a.getUsername(), a.getFullName(), depName, posName);
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
    }

    private void inBangDepartment(List<Department> list) {
        System.out.println("+----+-------------------------+");
        System.out.printf("| %-2s | %-23s |\n", "ID", "Department Name");
        System.out.println("+----+-------------------------+");
        for (Department d : list) {
            System.out.printf("| %-2d | %-23s |\n", d.getId(), d.getName());
        }
        System.out.println("+----+-------------------------+");
    }

    private int nhapSoNguyenDuong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int number = Integer.parseInt(sc.nextLine().trim());
                if (number > 0) return number;
                System.out.println("Lỗi: Vui lòng nhập một số nguyên lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Bạn phải nhập vào một số hợp lệ, không chứa chữ hoặc ký tự đặc biệt!");
            }
        }
    }

    private String nhapChuoiKhongRong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Lỗi: Dữ liệu không được để trống, vui lòng nhập lại!");
        }
    }
}