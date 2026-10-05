package frontend;

import backend.QLAccount;
import backend.QLDepartment;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        QLAccount qlAccount = new QLAccount();
        QLDepartment qlDepartment = new QLDepartment();

        while (true) {
            System.out.println("\n========= PHẦN MỀM QUẢN LÝ (BTVN BUỔI 8) =========");
            System.out.println("1. Quản lý Account");
            System.out.println("2. Quản lý Department");
            System.out.println("3. Thoát chương trình");
            System.out.print("Mời bạn chọn menu (1-3): ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    menuAccount(sc, qlAccount);
                    break;
                case "2":
                    menuDepartment(sc, qlDepartment);
                    break;
                case "3":
                    System.out.println("Đã thoát chương trình thành công!");
                    sc.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    // ---------------- MENU QUẢN LÝ ACCOUNT ----------------
    private static void menuAccount(Scanner sc, QLAccount qlAccount) {
        while (true) {
            System.out.println("\n--- QUẢN LÝ ACCOUNT ---");
            System.out.println("1. Hiển thị toàn bộ account");
            System.out.println("2. Tìm kiếm account theo username");
            System.out.println("3. Thêm mới account");
            System.out.println("4. Xóa account theo username");
            System.out.println("5. Update fullname theo username");
            System.out.println("6. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-6): ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": qlAccount.hienThiTatCa(); break;
                case "2": qlAccount.timKiemTheoUsername(); break;
                case "3": qlAccount.themMoi(); break;
                case "4": qlAccount.xoaTheoUsername(); break;
                case "5": qlAccount.capNhatFullName(); break;
                case "6": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ---------------- MENU QUẢN LÝ DEPARTMENT ----------------
    private static void menuDepartment(Scanner sc, QLDepartment qlDepartment) {
        while (true) {
            System.out.println("\n--- QUẢN LÝ DEPARTMENT ---");
            System.out.println("1. Hiển thị toàn bộ department");
            System.out.println("2. Tìm kiếm department theo tên");
            System.out.println("3. Thêm mới department");
            System.out.println("4. Xóa department theo ID");
            System.out.println("5. Update tên phòng ban theo ID");
            System.out.println("6. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-6): ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": qlDepartment.hienThiTatCa(); break;
                case "2": qlDepartment.timKiemTheoTen(); break;
                case "3": qlDepartment.themMoi(); break;
                case "4": qlDepartment.xoaTheoId(); break;
                case "5": qlDepartment.capNhatTen(); break;
                case "6": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}