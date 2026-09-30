package frontend;

import backend.IQLCB;
import backend.QLCB;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        IQLCB iqlcb = new QLCB();
        while (true) {
            System.out.println("==== Mời bạn chọn chức năng ====");
            System.out.println("1. Thêm mới cán bộ.");
            System.out.println("2. Tìm kiếm theo họ tên.");
            System.out.println("3. Hiển thị toàn bộ các cán bộ.");
            System.out.println("4. Nhập vào tên của cán bộ và delete cán bộ đó.");
            System.out.println("5. Thoát khỏi chương trình.");
            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    iqlcb.themMoi();
                    break;
                case "2":
                    iqlcb.timKiemTheoTen();
                    break;
                case "3":
                    iqlcb.hienThiToanBo();
                    break;
                case "4":
                    iqlcb.xoaTheoTen();
                    break;
                case "5":
                    System.out.println("Thoát.");
                    System.exit(0);
                default:
                    System.out.println("Chọn sai, Chọn lại!");
            }
        }

    }
}
