import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        QuanLySach ql = new QuanLySach();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== QUẢN LÝ THƯ VIỆN ==========");
            System.out.println("3. Hiển thị thông tin về tài liệu");
            System.out.println("4. Tìm kiếm tài liệu theo loại");
            System.out.println("5. Thoát khỏi chương trình");
            System.out.print("Mời bạn chọn chức năng (3-5): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 3:
                    ql.hienThiThongTin();
                    break;
                case 4:
                    System.out.print("Chọn loại muốn tìm (1. Sách | 2. Tạp chí | 3. Báo): ");
                    int loai = scanner.nextInt();
                    ql.timKiemTheoLoai(loai);
                    break;
                case 5:
                    System.out.println("Đã thoát chương trình!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Vui lòng chỉ chọn chức năng 3, 4 hoặc 5.");
                    break;
            }
        }
    }
}