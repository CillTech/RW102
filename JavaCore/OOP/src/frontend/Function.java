package frontend;

import backend.controller.CanBoController;
import entity.*;

import java.util.List;
import java.util.Scanner;

public class Function {

    private CanBoController controller = new CanBoController();
    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Function app = new Function();
        app.menu();
    }

    public void menu() {
        while (true) {
            System.out.println("\n==== PHẦN MỀM QUẢN LÝ CÁN BỘ ====");
            System.out.println("1. Thêm mới cán bộ.");
            System.out.println("2. Tìm kiếm theo họ tên.");
            System.out.println("3. Hiển thị toàn bộ các cán bộ.");
            System.out.println("4. Nhập vào tên của cán bộ và delete cán bộ đó.");
            System.out.println("5. Sửa địa chỉ cán bộ.");
            System.out.println("6. Thoát khỏi chương trình.");
            System.out.print("Mời bạn chọn chức năng (1-6): ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    themMoi();
                    break;
                case "2":
                    timKiemTheoTen();
                    break;
                case "3":
                    hienThiToanBo();
                    break;
                case "4":
                    xoaTheoTen();
                    break;
                case "5":
                    updateDiaChiTheoTen();
                    break;
                case "6":
                    System.out.println("Đã thoát chương trình.");
                    System.exit(0);
                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }
        }
    }

    public void themMoi() {
        System.out.println("\n==== THÊM MỚI CÁN BỘ ====");
        String loai;
        while (true) {
            System.out.print("Chọn loại cán bộ muốn thêm (1. Công nhân | 2. Kỹ sư | 3. Nhân viên): ");
            loai = sc.nextLine().trim();
            if (loai.equals("1") || loai.equals("2") || loai.equals("3")) break;
            System.out.println("Lựa chọn không hợp lệ, vui lòng nhập 1, 2 hoặc 3.");
        }

        String hoTen = nhapChuoiKhongRong("Nhập họ tên: ");
        int tuoi = nhapSoNguyenDuong("Nhập tuổi: ");

        int chonGioiTinh;
        while (true) {
            chonGioiTinh = nhapSoNguyenDuong("Nhập giới tính (1. NAM, 2. NU, 3. KHAC): ");
            if (chonGioiTinh >= 1 && chonGioiTinh <= 3) break;
            System.out.println("Vui lòng chỉ chọn 1, 2 hoặc 3.");
        }

        GioiTinh gioiTinh = (chonGioiTinh == 1) ? GioiTinh.NAM : (chonGioiTinh == 2) ? GioiTinh.NU : GioiTinh.KHAC;
        String diaChi = nhapChuoiKhongRong("Nhập địa chỉ: ");

        CanBo cb = null;
        switch (loai) {
            case "1":
                int bac = nhapSoNguyenDuong("Nhập bậc công nhân (số nguyên): ");
                cb = new CongNhan(hoTen, tuoi, gioiTinh, diaChi, Loai.CN, bac);
                break;
            case "2":
                String nganh = nhapChuoiKhongRong("Nhập ngành đào tạo kỹ sư: ");
                cb = new KySu(hoTen, tuoi, gioiTinh, diaChi, Loai.KS, nganh);
                break;
            case "3":
                String congViec = nhapChuoiKhongRong("Nhập công việc nhân viên: ");
                cb = new NhanVien(hoTen, tuoi, gioiTinh, diaChi, Loai.NV, congViec);
                break;
        }

        if (controller.themMoi(cb)) {
            System.out.println("Thêm mới vào Cơ sở dữ liệu thành công!");
        } else {
            System.out.println("Thêm mới thất bại!");
        }
    }

    public void timKiemTheoTen() {
        System.out.println("\n==== TÌM KIẾM CÁN BỘ ====");
        String ten = nhapChuoiKhongRong("Nhập họ tên cần tìm: ");

        List<CanBo> ketQua = controller.timKiemTheoTen(ten);
        inDanhSachCoFormat(ketQua);
    }

    public void hienThiToanBo() {
        System.out.println("\n==== HIỂN THỊ TOÀN BỘ CÁN BỘ ====");
        List<CanBo> danhSach = controller.layTatCa();
        inDanhSachCoFormat(danhSach);
    }

    public void xoaTheoTen() {
        System.out.println("\n==== XÓA CÁN BỘ ====");
        hienThiToanBo();

        String ten = nhapChuoiKhongRong("\nNhập chính xác họ tên cán bộ cần xóa: ");

        if (controller.xoaTheoTen(ten)) {
            System.out.println("Đã xóa thành công cán bộ tên: " + ten + " khỏi Database.");
        } else {
            System.out.println("Không tìm thấy cán bộ nào có tên là: " + ten + " hoặc xóa thất bại.");
        }
    }

    public void updateDiaChiTheoTen() {
        System.out.println("\n==== CẬP NHẬT ĐỊA CHỈ ====");
        hienThiToanBo();

        String ten = nhapChuoiKhongRong("\nNhập họ tên cán bộ cần cập nhật: ");
        String diaChiMoi = nhapChuoiKhongRong("Nhập địa chỉ mới: ");

        if (controller.updateDiaChiTheoTen(ten, diaChiMoi)) {
            System.out.println("Đã cập nhật địa chỉ thành công cho cán bộ: " + ten);
        } else {
            System.out.println("Cập nhật thất bại. Không tìm thấy cán bộ nào có tên là: " + ten);
        }
    }

    private void inDanhSachCoFormat(List<CanBo> canBoList) {
        System.out.println("+-------------------------+-------+----------+--------------------+---------------+-------------------------+");
        System.out.printf("| %-23s | %-5s | %-8s | %-18s | %-13s | %-23s |\n", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ", "Loại", "Thông tin riêng");
        System.out.println("+-------------------------+-------+----------+--------------------+---------------+-------------------------+");

        if (canBoList == null || canBoList.isEmpty()) {
            System.out.printf("| %-98s |\n", "Không có dữ liệu / Không tìm thấy kết quả");
        } else {
            for (CanBo cb : canBoList) {
                String thongTinRieng = "";
                if (cb.getLoai().equals(Loai.CN)) {
                    thongTinRieng = "Bậc: " + ((CongNhan) cb).getBac();
                } else if (cb.getLoai().equals(Loai.KS)) {
                    thongTinRieng = "Ngành: " + ((KySu) cb).getNganhDaoTao();
                } else if (cb.getLoai().equals(Loai.NV)) {
                    thongTinRieng = "Công việc: " + ((NhanVien) cb).getCongViec();
                }
                System.out.printf("| %-23s | %-5s | %-8s | %-18s | %-13s | %-23s |\n",
                        cb.getHoTen(), cb.getTuoi(), cb.getGioiTinh(), cb.getDiaChi(), cb.getLoai(), thongTinRieng);
            }
        }
        System.out.println("+-------------------------+-------+----------+--------------------+---------------+-------------------------+");
    }

    private int nhapSoNguyenDuong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int number = Integer.parseInt(sc.nextLine().trim());
                if (number > 0) return number;
                System.out.println("Vui lòng nhập một số lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Bạn phải nhập vào một số nguyên hợp lệ!");
            }
        }
    }

    private String nhapChuoiKhongRong(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Lỗi: Dữ liệu không được để trống, vui lòng nhập lại!");
        }
    }
}