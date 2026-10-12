package frontend;

import backend.controller.DepartmentController;
import backend.controller.PositionController;
import entity.Account;
import entity.Department;
import entity.Position;

import java.util.List;
import java.util.Scanner;

public class Function {
    protected Scanner sc = new Scanner(System.in);
    protected DepartmentController depController = new DepartmentController();
    protected PositionController posController = new PositionController();

    public void menu() {
        AccountFunction accFunction = new AccountFunction();
        DepartmentFunction depFunction = new DepartmentFunction();
        PositionFunction posFunction = new PositionFunction();

        while (true) {
            System.out.println("\n========= PHẦN MỀM QUẢN LÝ =========");
            System.out.println("1. Quản lý Account");
            System.out.println("2. Quản lý Department");
            System.out.println("3. Quản lý Position");
            System.out.println("4. Thoát chương trình");
            System.out.print("Mời bạn chọn menu (1-4): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": accFunction.menuAccount(); break;
                case "2": depFunction.menuDepartment(); break;
                case "3": posFunction.menuPosition(); break;
                case "4":
                    System.out.println("Đã thoát chương trình thành công!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại!");
            }
        }
    }

    public int nhapSoNguyenDuong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int number = Integer.parseInt(sc.nextLine().trim());
                if (number > 0) return number;
                System.out.println("Lỗi: Vui lòng nhập một số nguyên lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Bạn phải nhập vào một số hợp lệ!");
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

    // Check tồn tại trực tiếp qua DB
    public int nhapIdDepartmentTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (depController.isDepartmentIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Phòng ban không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
    }

    public int nhapIdPositionTonTai(String prompt) {
        while (true) {
            int id = nhapSoNguyenDuong(prompt);
            if (posController.isPositionIdTonTai(id)) {
                return id;
            }
            System.out.println("Lỗi: ID Vị trí không tồn tại trong hệ thống. Vui lòng nhập lại!");
        }
    }

    public void inBangAccount(List<Account> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        System.out.printf("| %-2s | %-25s | %-15s | %-18s | %-23s | %-18s |\n", "ID", "Email", "Username", "Full Name", "Department", "Position");
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        for (Account a : list) {
            String depName = (a.getDepartment() != null && a.getDepartment().getName() != null)
                    ? a.getDepartment().getName() : "Chưa có";
            String posName = (a.getPosition() != null && a.getPosition().getName() != null)
                    ? a.getPosition().getName() : "Chưa có";
            System.out.printf("| %-2d | %-25s | %-15s | %-18s | %-23s | %-18s |\n",
                    a.getId(), a.getEmail(), a.getUsername(), a.getFullName(), depName, posName);
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
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

    public void inBangPosition(List<Position> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.println("+----+-------------------------+");
        System.out.printf("| %-2s | %-23s |\n", "ID", "Position Name");
        System.out.println("+----+-------------------------+");
        for (Position p : list) {
            System.out.printf("| %-2d | %-23s |\n", p.getId(), p.getName());
        }
        System.out.println("+----+-------------------------+");
    }
}