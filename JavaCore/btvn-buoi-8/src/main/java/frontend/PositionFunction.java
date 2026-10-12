package frontend;

import entity.Position;

import java.util.List;

public class PositionFunction extends Function {

    public void menuPosition() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ POSITION ---");
            System.out.println("1. Hiển thị toàn bộ position");
            System.out.println("2. Tìm kiếm position theo tên");
            System.out.println("3. Thêm mới position");
            System.out.println("4. Xóa position theo ID");
            System.out.println("5. Update tên position theo ID");
            System.out.println("6. Quay lại menu chính");
            System.out.print("Chọn chức năng (1-6): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": hienThiPosition(); break;
                case "2": timKiemPosition(); break;
                case "3": themMoiPosition(); break;
                case "4": xoaPosition(); break;
                case "5": capNhatPosition(); break;
                case "6": return;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    public void hienThiPosition() {
        System.out.println("==== DANH SÁCH POSITION ====");
        inBangPosition(posController.layTatCa());
    }

    private void timKiemPosition() {
        String name = nhapChuoiKhongRong("Nhập tên vị trí cần tìm: ");
        List<Position> kq = posController.timKiemTheoTen(name);
        if (kq.isEmpty()) System.out.println("Không tìm thấy kết quả nào!");
        else inBangPosition(kq);
    }

    private void themMoiPosition() {
        String name;
        while (true) {
            name = nhapChuoiKhongRong("Nhập tên vị trí mới: ");
            if (posController.isPositionNameTonTai(name)) {
                System.out.println("Lỗi: Tên vị trí đã tồn tại trong DB, vui lòng nhập lại!");
            } else {
                break;
            }
        }

        if (posController.themMoi(name)) System.out.println("Thêm thành công!");
        else System.out.println("Thêm thất bại!");
    }

    private void xoaPosition() {
        hienThiPosition();
        int id = nhapIdPositionTonTai("\nNhập ID position cần xóa: ");
        if (posController.xoaTheoId(id)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Xóa thất bại (Vị trí đang có nhân viên phụ thuộc)!");
        }
    }

    private void capNhatPosition() {
        hienThiPosition();
        int id = nhapIdPositionTonTai("\nNhập ID position cần sửa: ");
        String newName = nhapChuoiKhongRong("Nhập tên position mới: ");

        if (posController.capNhatTen(id, newName)) System.out.println("Cập nhật thành công!");
        else System.out.println("Cập nhật thất bại!");
    }
}