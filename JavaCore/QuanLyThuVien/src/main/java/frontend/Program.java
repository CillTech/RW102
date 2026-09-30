package frontend;

import backend.*;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        QuanLySach ql = new QuanLySach();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== QUẢN LÝ THƯ VIỆN ==========");
            System.out.println("1. Thêm mới tài liệu");
            System.out.println("2. Xóa tài liệu theo mã");
            System.out.println("3. Hiển thị thông tin tài liệu");
            System.out.println("4. Tìm kiếm gần đúng mã tài liệu");
            System.out.println("5. Thoát khỏi chương trình");
            System.out.print("Mời bạn chọn (1-5): ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    ql.themMoi();
                    break;
                case "2":
                    ql.xoaTaiLieu();
                    break;
                case "3":
                    ql.hienThiThongTin();
                    break;
                case "4":
                    ql.timKiemGanDung();
                    break;
                case "5":
                    System.out.println("Thoát chương trình. Hẹn gặp lại!");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn sai, vui lòng nhập lại!");
                    break;
            }
        }
    }
}