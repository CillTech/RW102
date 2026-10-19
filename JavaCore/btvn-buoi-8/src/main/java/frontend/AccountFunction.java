package frontend;

import backend.controller.AccountController;
import entity.Account;
import entity.Department;
import entity.Position;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountFunction extends Function {
    private AccountController accController = new AccountController();

    public void menuAccount() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ACCOUNT ---");
            System.out.println("1. Hiển thị toàn bộ account");
            System.out.println("2. Tìm kiếm account theo username");
            System.out.println("3. Tìm kiếm account theo tên (Stored Procedure)");
            System.out.println("4. Thêm mới account");
            System.out.println("5. Xóa account theo ID");
            System.out.println("6. Update username theo ID");
            System.out.println("7. Import account từ file CSV");
            System.out.println("8. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-8): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hienThiAccount(); break;
                case "2": timKiemAccount(); break;
                case "3": timKiemTheoTenSP(); break;
                case "4": themMoiAccount(); break;
                case "5": xoaAccount(); break;
                case "6": capNhatAccount(); break;
                case "7": importAccountFromCSV(); break;
                case "8": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void importAccountFromCSV() {
        System.out.print("Nhập đường dẫn file CSV (mặc định 'account.csv'): ");
        String filePath = sc.nextLine().trim();
        if (filePath.isEmpty()) {
            filePath = "account.csv";
        }

        List<Account> validAccounts = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
             BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || (lineNumber == 1 && line.toLowerCase().startsWith("email"))) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 5) {
                    System.out.println("[-] Dòng " + lineNumber + ": Không đủ 5 cột dữ liệu -> Bỏ qua.");
                    continue;
                }

                String email = parts[0].trim();
                String username = parts[1].trim();
                String fullName = parts[2].trim();
                String depIdStr = parts[3].trim();
                String posIdStr = parts[4].trim();

                if (accController.isEmailTonTai(email)) {
                    System.out.println("[-] Dòng " + lineNumber + ": Email '" + email + "' đã tồn tại trong DB -> Bỏ qua.");
                    continue;
                }
                if (accController.isUsernameTonTai(username)) {
                    System.out.println("[-] Dòng " + lineNumber + ": Username '" + username + "' đã tồn tại trong DB -> Bỏ qua.");
                    continue;
                }

                int depId;
                int posId;
                try {
                    depId = Integer.parseInt(depIdStr);
                    posId = Integer.parseInt(posIdStr);
                } catch (NumberFormatException e) {
                    System.out.println("[-] Dòng " + lineNumber + ": Department ID hoặc Position ID không phải số hợp lệ -> Bỏ qua.");
                    continue;
                }

                Department dep = depController.layTheoId(depId);
                if (dep == null) {
                    System.out.println("[-] Dòng " + lineNumber + ": Department ID = " + depId + " không tồn tại trong DB -> Bỏ qua.");
                    continue;
                }

                Position pos = posController.layTheoId(posId);
                if (pos == null) {
                    System.out.println("[-] Dòng " + lineNumber + ": Position ID = " + posId + " không tồn tại trong DB -> Bỏ qua.");
                    continue;
                }

                Account acc = new Account();
                acc.setEmail(email);
                acc.setUsername(username);
                acc.setFullName(fullName);
                acc.setDepartment(dep);
                acc.setPosition(pos);
                validAccounts.add(acc);
            }

            if (!validAccounts.isEmpty()) {
                int inserted = accController.themDanhSach(validAccounts);
                System.out.println("\n>> Đã lưu thành công " + inserted + "/" + validAccounts.size() + " account vào cơ sở dữ liệu.");
            } else {
                System.out.println("\n>> Không có dữ liệu hợp lệ để lưu vào DB.");
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi đọc file CSV: " + e.getMessage());
        }
    }

    private void timKiemTheoTenSP() {
        String name = nhapChuoiKhongRong("Nhập họ tên cần tìm kiếm: ");
        List<Account> kq = accController.timKiemTheoTenSP(name);
        if (kq.isEmpty()) {
            System.out.println("Không tìm thấy account nào có tên chứa: " + name);
        } else {
            inBangAccount(kq);
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
        inBangDepartment(depController.layTatCa());
        int depId = nhapIdDepartmentTonTai("Mời bạn nhập ID Phòng ban: ");

        System.out.println("\n--- Danh sách Vị trí hiện có ---");
        inBangPosition(posController.layTatCa());
        int posId = nhapIdPositionTonTai("Mời bạn nhập ID Vị trí: ");

        Account newAcc = new Account();
        newAcc.setEmail(email);
        newAcc.setUsername(username);
        newAcc.setFullName(fullName);
        newAcc.setDepartment(new Department(depId, null));
        newAcc.setPosition(new Position(posId, null));

        if (accController.themMoi(newAcc)) {
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
        String newUsername = nhapUsernameHopLe(id);

        if (accController.capNhatUsername(id, newUsername)) {
            System.out.println("Cập nhật Username thành công!");
        } else {
            System.out.println("Lỗi cơ sở dữ liệu: Cập nhật thất bại!");
        }
    }

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
        return nhapUsernameHopLe(-1);
    }

    private String nhapUsernameHopLe(int currentId) {
        while (true) {
            String input = nhapChuoiDoDai("Nhập Username (5-50 kí tự): ", 5, 50);

            boolean isDuplicate = (currentId <= 0)
                    ? accController.isUsernameTonTai(input)
                    : accController.isUsernameTonTai(input, currentId);

            if (isDuplicate) {
                System.out.println("Lỗi: Username này đã tồn tại, vui lòng nhập lại!");
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
}