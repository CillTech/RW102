package frontend;

import backend.controller.AccountController;
import entity.Account;

import java.util.List;
import java.util.regex.Pattern;

public class AccountFunction extends Function {
    private AccountController accController = new AccountController();
    private DepartmentFunction depFunction = new DepartmentFunction();

    public void menuAccount() {
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

    public void hienThiAccount() {
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
        depFunction.hienThiDepartment();
        int depId = depFunction.nhapIdDepartmentTonTai("Mời bạn nhập ID Phòng ban: ");

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

    // ================= VALIDATION RIÊNG CHO ACCOUNT =================

    private String nhapEmailHopLe() {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern p = Pattern.compile(emailRegex);
        while (true) {
            String input = nhapChuoiDoDai("Nhập Email (5-50 kí tự): ", 5, 50);
            if (!p.matcher(input).matches()) {
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

    private int nhapIdAccountTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (accController.isAccountIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Account này không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
    }

    private void inBangAccount(List<Account> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        System.out.printf("| %-2s | %-25s | %-15s | %-18s | %-23s | %-18s |\n", "ID", "Email", "Username", "Full Name", "Department", "Position");
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        for (Account a : list) {
            String depName = (a.getDepartment() != null && a.getDepartment().getName() != null)
                    ? a.getDepartment().getName()
                    : "Chưa có";

            String posName = (a.getPosition() != null && a.getPosition().getName() != null)
                    ? a.getPosition().getName()
                    : "Chưa có";
            System.out.printf("| %-2d | %-25s | %-15s | %-18s | %-23s | %-18s |\n",
                    a.getId(), a.getEmail(), a.getUsername(), a.getFullName(), depName, posName);
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
    }
}