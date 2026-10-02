package frontend;

import backend.QuanLyHeThong;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        QuanLyHeThong ql = new QuanLyHeThong();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========= CHỌN CHỨC NĂNG =========");
            System.out.println("1. Hiển thị toàn bộ account");
            System.out.println("2. Tìm kiếm account theo username");
            System.out.println("3. Hiển thị toàn bộ department");
            System.out.println("4. Tìm kiếm department theo tên");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng (1-5): ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    ql.showAllAccounts();
                    break;
                case "2":
                    System.out.print("Nhập username cần tìm: ");
                    String username = sc.nextLine();
                    ql.searchAccountByUsername(username);
                    break;
                case "3":
                    ql.showAllDepartments();
                    break;
                case "4":
                    System.out.print("Nhập tên Department cần tìm: ");
                    String depName = sc.nextLine();
                    ql.searchDepartmentByName(depName);
                    break;
                case "5":
                    System.out.println("Đã thoát chương trình!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}