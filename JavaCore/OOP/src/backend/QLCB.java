package backend;

import entity.CanBo;
import entity.CongNhan;
import entity.GioiTinh;
import entity.KySu;
import entity.NhanVien;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLCB implements IQLCB {

    private List<CanBo> canBoList;
    private Scanner sc = new Scanner(System.in);

    @Override
    public void themMoi() {
        System.out.println("==== THÊM MỚI CÁN BỘ ====");
        System.out.println("Chọn loại cán bộ muốn thêm: 1. Công nhân | 2. Kỹ sư | 3. Nhân viên");
        String loai = sc.nextLine();

        System.out.print("Nhập họ tên: ");
        String hoTen = sc.nextLine();

        // Sử dụng Integer.parseInt(sc.nextLine()) để tránh bẫy trôi lệnh (Scanner newline issue)
        System.out.print("Nhập tuổi: ");
        int tuoi = Integer.parseInt(sc.nextLine());

        System.out.print("Nhập giới tính (1. NAM, 2. NU, 3. KHAC): ");
        int chonGioiTinh = Integer.parseInt(sc.nextLine());
        GioiTinh gioiTinh = GioiTinh.KHAC;
        if (chonGioiTinh == 1) {
            gioiTinh = GioiTinh.NAM;
        } else if (chonGioiTinh == 2) {
            gioiTinh = GioiTinh.NU;
        }

        System.out.print("Nhập địa chỉ: ");
        String diaChi = sc.nextLine();

        // Tùy thuộc vào loại cán bộ để nhập thêm thuộc tính riêng và khởi tạo Object tương ứng
        switch (loai) {
            case "1":
                System.out.print("Nhập bậc công nhân (số nguyên): ");
                int bac = Integer.parseInt(sc.nextLine());
                CanBo cn = new CongNhan(hoTen, tuoi, gioiTinh, diaChi, bac);
                canBoList.add(cn);
                System.out.println("Thêm mới Công nhân thành công!");
                break;
            case "2":
                System.out.print("Nhập ngành đào tạo kỹ sư: ");
                String nganh = sc.nextLine();
                CanBo ks = new KySu(hoTen, tuoi, gioiTinh, diaChi, nganh);
                canBoList.add(ks);
                System.out.println("Thêm mới Kỹ sư thành công!");
                break;
            case "3":
                System.out.print("Nhập công việc nhân viên: ");
                String congViec = sc.nextLine();
                CanBo nv = new NhanVien(hoTen, tuoi, gioiTinh, diaChi, congViec);
                canBoList.add(nv);
                System.out.println("Thêm mới Nhân viên thành công!");
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Hủy thêm mới.");
                break;
        }
    }

    @Override
    public void timKiemTheoTen() {
        System.out.println("==== TÌM KIẾM CÁN BỘ ====");
        System.out.print("Nhập họ tên cần tìm: ");
        String ten = sc.nextLine();
        System.out.println("+-------------------------+-----+----------+--------------------+");
        System.out.printf("|%25s|%5s|%10s|%20s|\n", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ");
        System.out.println("+-------------------------+-----+----------+--------------------+");
        boolean timThay = false;
        for (CanBo cb : canBoList) {
            if (cb.getHoTen().contains(ten)) {
                System.out.printf("|%25s|%5s|%10s|%20s|\n", cb.getHoTen(), cb.getTuoi(), cb.getGioiTinh(), cb.getDiaChi());
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("Không tìm thấy kết quả nào.");
        }
        System.out.println("+-------------------------+-----+----------+--------------------+");
    }

    @Override
    public void hienThiToanBo() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ CÁN BỘ ====");
        System.out.println("+-------------------------+-----+----------+--------------------+");
        System.out.printf("|%25s|%5s|%10s|%20s|\n", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ");
        System.out.println("+-------------------------+-----+----------+--------------------+");
        for (CanBo cb : canBoList) {
            System.out.printf("|%25s|%5s|%10s|%20s|\n", cb.getHoTen(), cb.getTuoi(), cb.getGioiTinh(), cb.getDiaChi());
        }
        System.out.println("+-------------------------+-----+----------+--------------------+");
    }

    @Override
    public void xoaTheoTen() {
        System.out.println("==== XÓA CÁN BỘ ====");
        System.out.print("Nhập họ tên cán bộ cần xóa (xóa chính xác): ");
        String ten = sc.nextLine();

        // Dùng removeIf để xóa an toàn trong List, tránh lỗi ConcurrentModificationException
        boolean xoaThanhCong = canBoList.removeIf(cb -> cb.getHoTen().equals(ten));

        if (xoaThanhCong) {
            System.out.println("Đã xóa thành công cán bộ tên: " + ten);
        } else {
            System.out.println("Không tìm thấy cán bộ nào có tên chính xác là: " + ten);
        }
    }
}