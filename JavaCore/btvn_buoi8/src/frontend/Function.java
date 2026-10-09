package frontend;

import java.util.Scanner;

public class Function {
    protected Scanner sc = new Scanner(System.in);

    public void menu() {
        AccountFunction accFunction = new AccountFunction();
        DepartmentFunction depFunction = new DepartmentFunction();

        while (true) {
            System.out.println("\n========= PHẦN MỀM QUẢN LÝ =========");
            System.out.println("1. Quản lý Account");
            System.out.println("2. Quản lý Department");
            System.out.println("3. Thoát chương trình");
            System.out.print("Mời bạn chọn menu (1-3): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    accFunction.menuAccount();
                    break;
                case "2":
                    depFunction.menuDepartment();
                    break;
                case "3":
                    System.out.println("Đã thoát chương trình thành công!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    // ================= CÁC HÀM BỔ TRỢ DÙNG CHUNG =================

    public int nhapSoNguyenDuong(String prompt) {
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

    public String nhapChuoiKhongRong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Lỗi: Dữ liệu không được để trống, vui lòng nhập lại!");
        }
    }

    public String nhapChuoiDoDai(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (input.length() >= min && input.length() <= max) {
                return input;
            }
            System.out.println("Lỗi: Dữ liệu phải từ " + min + " đến " + max + " kí tự!");
        }
    }
}