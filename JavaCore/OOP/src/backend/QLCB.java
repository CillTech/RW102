package backend;

import dao.CanBoRepository;
import entity.*;

import java.util.List;
import java.util.Scanner;

public class QLCB implements IQLCB {

    private CanBoRepository repo = new CanBoRepository();
    private Scanner sc = new Scanner(System.in);

    @Override
    public void themMoi() {
        System.out.println("==== THÊM MỚI CÁN BỘ ====");
        System.out.println("Chọn loại cán bộ muốn thêm: 1. Công nhân | 2. Kỹ sư | 3. Nhân viên");
        String loai = sc.nextLine();

        System.out.print("Nhập họ tên: ");
        String hoTen = sc.nextLine();

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

        CanBo cb = null;
        switch (loai) {
            case "1":
                System.out.print("Nhập bậc công nhân (số nguyên): ");
                int bac = Integer.parseInt(sc.nextLine());
                cb = new CongNhan(hoTen, tuoi, gioiTinh, diaChi, Loai.CN, bac);
                break;
            case "2":
                System.out.print("Nhập ngành đào tạo kỹ sư: ");
                String nganh = sc.nextLine();
                cb = new KySu(hoTen, tuoi, gioiTinh, diaChi, Loai.KS, nganh);
                break;
            case "3":
                System.out.print("Nhập công việc nhân viên: ");
                String congViec = sc.nextLine();
                cb = new NhanVien(hoTen, tuoi, gioiTinh, diaChi, Loai.NV, congViec);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ. Hủy thêm mới.");
                return;
        }

        if (repo.themMoi(cb)) {
            System.out.println("Thêm mới vào Cơ sở dữ liệu thành công!");
        } else {
            System.out.println("Thêm mới thất bại!");
        }
    }

    @Override
    public void timKiemTheoTen() {
        System.out.println("==== TÌM KIẾM CÁN BỘ ====");
        System.out.print("Nhập họ tên cần tìm: ");
        String ten = sc.nextLine();

        List<CanBo> ketQua = repo.timKiemTheoTen(ten);

        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");
        System.out.printf("|%25s|%5s|%10s|%20s|%15s|%25s|\n", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ", "Loại", "Thông tin riêng");
        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");

        if (ketQua.isEmpty()) {
            System.out.println("Không tìm thấy kết quả nào.");
        } else {
            inDanhSach(ketQua);
        }
        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");
    }

    @Override
    public void hienThiToanBo() {
        System.out.println("==== HIỂN THỊ TOÀN BỘ CÁN BỘ ====");
        List<CanBo> danhSach = repo.layTatCa();

        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");
        System.out.printf("|%25s|%5s|%10s|%20s|%15s|%25s|\n", "Họ tên", "Tuổi", "Giới tính", "Địa chỉ", "Loại", "Thông tin riêng");
        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");

        if (!danhSach.isEmpty()) {
            inDanhSach(danhSach);
        } else {
            System.out.println("Không có dữ liệu trong Database");
        }
        System.out.println("+-------------------------+-----+----------+--------------------+---------------+-------------------------+");
    }

    // Hàm phụ trợ in danh sách để rút gọn code
    private void inDanhSach(List<CanBo> canBoList) {
        for (CanBo cb : canBoList) {
            String thongTinRieng = "";
            if (cb.getLoai().equals(Loai.CN)) {
                thongTinRieng = "Bậc: " + ((CongNhan) cb).getBac();
            } else if (cb.getLoai().equals(Loai.KS)) {
                thongTinRieng = "Ngành: " + ((KySu) cb).getNganhDaoTao();
            } else if (cb.getLoai().equals(Loai.NV)) {
                thongTinRieng = "Công việc: " + ((NhanVien) cb).getCongViec();
            }
            System.out.printf("|%25s|%5s|%10s|%20s|%15s|%25s|\n",
                    cb.getHoTen(), cb.getTuoi(), cb.getGioiTinh(), cb.getDiaChi(), cb.getLoai(), thongTinRieng);
        }
    }

    @Override
    public void xoaTheoTen() {
        System.out.println("==== XÓA CÁN BỘ ====");
        System.out.print("Nhập họ tên cán bộ cần xóa: ");
        String ten = sc.nextLine();

        if (repo.xoaTheoTen(ten)) {
            System.out.println("Đã xóa thành công cán bộ tên: " + ten + " khỏi Database.");
        } else {
            System.out.println("Không tìm thấy cán bộ nào có tên chính xác là: " + ten + " hoặc xóa thất bại.");
        }
    }
}