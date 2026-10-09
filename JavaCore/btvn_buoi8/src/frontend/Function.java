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

    public static void main(String[] args) {
        Function app = new Function();
        app.menu();
    }

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
            System.out.println("4. Xóa account theo ID");
            System.out.println("5. Update username theo ID");
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

        String email = nhapEmailHopLe();
        String username = nhapUsernameHopLe();
        String fullName = nhapChuoiDoDai("Nhập Full Name (5-50 kí tự): ", 5, 50);

        System.out.println("\n--- Danh sách Phòng ban hiện có ---");
        inBangDepartment(depController.layTatCa());
        int depId = nhapIdDepartmentTonTai("Mời bạn nhập ID Phòng ban: ");

        System.out.println("\n--- Danh sách Vị trí ---");
        System.out.println("1. Dev | 2. Test | 3. Scrum Master | 4. PM");
        int posId;
        while (true) {
            posId = nhapSoNguyenDuong("Mời bạn nhập ID Vị trí (1-4): ");
            if (posId >= 1 && posId <= 4) break;
            System.out.println("Lỗi: Vị trí không hợp lệ, vui lòng chọn từ 1 đến 4!");
        }

        if (accController.themMoi(email, username, fullName, depId, posId)) {
            System.out.println("Thêm Account thành công!");
        } else {
            System.out.println("Lỗi cơ sở dữ liệu: Thêm thất bại!");
        }
    }

    private void xoaAccount() {
        System.out.println("==== XÓA ACCOUNT ====");
        inBangAccount(accController.layTatCa());

        int id = nhapIdAccountTonTai("\nNhập ID account cần xóa: ");
        if (accController.xoaTheoId(id)) {
            System.out.println("Xóa thành công account ID: " + id);
        } else {
            System.out.println("Lỗi cơ sở dữ liệu: Xóa thất bại.");
        }
    }

    private void capNhatAccount() {
        System.out.println("==== CẬP NHẬT USERNAME ====");
        inBangAccount(accController.layTatCa());

        int id = nhapIdAccountTonTai("\nNhập ID account cần cập nhật: ");
        String newUsername = nhapUsernameHopLe();

        if (accController.capNhatUsername(id, newUsername)) {
            System.out.println("Cập nhật Username thành công!");
        } else {
            System.out.println("Lỗi cơ sở dữ liệu: Cập nhật thất bại!");
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

    // ================= HÀM HỖ TRỢ VALIDATION =================

    private String nhapEmailHopLe() {
        while (true) {
            String input = nhapChuoiDoDai("Nhập Email (5-50 kí tự): ", 5, 50);
            if (!input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9+_.-]+\\.[A-Za-z0-9.]+$")) {
                System.out.println("Lỗi: Email không đúng định dạng (VD: example@gmail.com)!");
                continue;
            }
            if (accController.isEmailTonTai(input)) {
                System.out.println("Lỗi: Email này đã tồn tại trong DB, vui lòng nhập lại!");
            } else {
                return input;
            }
        }
    }

    private String nhapUsernameHopLe() {
        while (true) {
            String input = nhapChuoiDoDai("Nhập Username (5-50 kí tự): ", 5, 50);
            if (accController.isUsernameTonTai(input)) {
                System.out.println("Lỗi: Username này đã tồn tại trong DB, vui lòng nhập lại!");
            } else {
                return input;
            }
        }
    }

    private String nhapChuoiDoDai(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.length() >= min && input.length() <= max) {
                return input;
            }
            System.out.println("Lỗi: Dữ liệu phải từ " + min + " đến " + max + " kí tự!");
        }
    }

    private int nhapIdAccountTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (accController.isAccountIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Account này không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
    }

    private int nhapIdDepartmentTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (depController.isDepartmentIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Phòng ban không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
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

    // ---------------- HÀM IN BẢNG ----------------

    private void inBangAccount(List<Account> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
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