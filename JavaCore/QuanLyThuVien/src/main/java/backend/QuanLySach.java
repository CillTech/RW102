package backend;

import entity.Bao;
import entity.Sach;
import entity.TaiLieu;
import entity.TapChi;
import dao.TaiLieuRepository;

import java.util.List;
import java.util.Scanner;

public class QuanLySach {

    private TaiLieuRepository repo = new TaiLieuRepository();
    private Scanner sc = new Scanner(System.in);

    // --- CÁC HÀM VALIDATE DỮ LIỆU ---

    private String nhapChuoiKhongRong(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String input = sc.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Lỗi: Dữ liệu không được để trống. Vui lòng nhập lại!");
        }
    }

    private int nhapSoNguyenDuong(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input > 0) {
                    return input;
                }
                System.out.println("Lỗi: Vui lòng nhập số nguyên lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Định dạng không hợp lệ. Vui lòng nhập số nguyên!");
            }
        }
    }

    private int nhapSoTrongKhoang(String thongBao, int min, int max) {
        while (true) {
            try {
                System.out.print(thongBao);
                int input = Integer.parseInt(sc.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Lỗi: Vui lòng nhập giá trị từ " + min + " đến " + max + "!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Định dạng không hợp lệ. Vui lòng nhập số nguyên!");
            }
        }
    }

    // --- CÁC HÀM XỬ LÝ NGHIỆP VỤ CHÍNH ---

    public void themMoi() {
        System.out.println("==== THÊM MỚI TÀI LIỆU ====");
        String loai;
        while (true) {
            System.out.println("Chọn loại tài liệu cần thêm: 1. Sách | 2. Tạp chí | 3. Báo");
            System.out.print("Lựa chọn của bạn: ");
            loai = sc.nextLine().trim();
            if (loai.equals("1") || loai.equals("2") || loai.equals("3")) {
                break;
            }
            System.out.println("Lỗi: Vui lòng chỉ nhập 1, 2 hoặc 3!");
        }

        String ma = nhapChuoiKhongRong("Nhập mã tài liệu: ");
        String nxb = nhapChuoiKhongRong("Nhập tên nhà xuất bản: ");
        int soBan = nhapSoNguyenDuong("Nhập số bản phát hành: ");

        TaiLieu tl = null;
        switch (loai) {
            case "1":
                String tacGia = nhapChuoiKhongRong("Nhập tên tác giả: ");
                int soTrang = nhapSoNguyenDuong("Nhập số trang: ");
                tl = new Sach(ma, nxb, soBan, tacGia, soTrang);
                break;
            case "2":
                int soPH = nhapSoNguyenDuong("Nhập số phát hành: ");
                int thangPH = nhapSoTrongKhoang("Nhập tháng phát hành (1-12): ", 1, 12);
                tl = new TapChi(ma, nxb, soBan, soPH, thangPH);
                break;
            case "3":
                int ngayPH = nhapSoTrongKhoang("Nhập ngày phát hành (1-31): ", 1, 31);
                tl = new Bao(ma, nxb, soBan, ngayPH);
                break;
        }

        if (tl != null && repo.themMoi(tl)) {
            System.out.println("Thêm mới vào Cơ sở dữ liệu thành công!");
        } else {
            System.out.println("Thêm mới thất bại! (Mã tài liệu có thể đã tồn tại trong Database)");
        }
    }

    public void xoaTaiLieu() {
        System.out.println("==== XÓA TÀI LIỆU ====");
        String maXoa = nhapChuoiKhongRong("Nhập mã tài liệu cần xóa (xóa chính xác): ");

        if (repo.xoaTheoMa(maXoa)) {
            System.out.println("Đã xóa thành công tài liệu mã: " + maXoa);
        } else {
            System.out.println("Không tìm thấy mã tài liệu này hoặc xóa thất bại.");
        }
    }

    public void hienThiThongTin() {
        System.out.println("==== DANH SÁCH TÀI LIỆU TỪ DATABASE ====");
        List<TaiLieu> danhSach = repo.layTatCa();

        if (danhSach.isEmpty()) {
            System.out.println("Chưa có tài liệu nào trong Database.");
            return;
        }
        for (TaiLieu tl : danhSach) {
            if (tl instanceof Sach) {
                System.out.println("[SÁCH] " + tl.toString());
            } else if (tl instanceof TapChi) {
                System.out.println("[TẠP CHÍ] " + tl.toString());
            } else if (tl instanceof Bao) {
                System.out.println("[BÁO] " + tl.toString());
            }
        }
    }

    public void timKiemGanDung() {
        System.out.println("==== TÌM KIẾM TÀI LIỆU ====");
        String tuKhoa = nhapChuoiKhongRong("Nhập từ khóa mã tài liệu cần tìm: ");

        List<TaiLieu> ketQua = repo.timKiemGanDung(tuKhoa);

        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        if (ketQua.isEmpty()) {
            System.out.println("Không có tài liệu nào chứa mã: " + tuKhoa);
        } else {
            for (TaiLieu tl : ketQua) {
                if (tl instanceof Sach) {
                    System.out.println("[SÁCH] " + tl.toString());
                } else if (tl instanceof TapChi) {
                    System.out.println("[TẠP CHÍ] " + tl.toString());
                } else if (tl instanceof Bao) {
                    System.out.println("[BÁO] " + tl.toString());
                }
            }
        }
    }
}