package backend;

import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLySach {
    private List<TaiLieu> danhSach = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

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

    private boolean kiemTraTrungMa(String maTaiLieu) {
        for (TaiLieu tl : danhSach) {
            if (tl.getMaTaiLieu().equals(maTaiLieu)) {
                return true;
            }
        }
        return false;
    }

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

        String ma;
        while (true) {
            ma = nhapChuoiKhongRong("Nhập mã tài liệu: ");
            if (!kiemTraTrungMa(ma)) {
                break;
            }
            System.out.println("Lỗi: Mã tài liệu đã tồn tại trong hệ thống. Vui lòng nhập mã khác!");
        }

        String nxb = nhapChuoiKhongRong("Nhập tên nhà xuất bản: ");
        int soBan = nhapSoNguyenDuong("Nhập số bản phát hành: ");

        switch (loai) {
            case "1":
                String tacGia = nhapChuoiKhongRong("Nhập tên tác giả: ");
                int soTrang = nhapSoNguyenDuong("Nhập số trang: ");
                danhSach.add(new Sach(ma, nxb, soBan, tacGia, soTrang));
                System.out.println("Đã thêm Sách thành công!");
                break;
            case "2":
                int soPH = nhapSoNguyenDuong("Nhập số phát hành: ");
                int thangPH = nhapSoTrongKhoang("Nhập tháng phát hành (1-12): ", 1, 12);
                danhSach.add(new TapChi(ma, nxb, soBan, soPH, thangPH));
                System.out.println("Đã thêm Tạp chí thành công!");
                break;
            case "3":
                int ngayPH = nhapSoTrongKhoang("Nhập ngày phát hành (1-31): ", 1, 31);
                danhSach.add(new Bao(ma, nxb, soBan, ngayPH));
                System.out.println("Đã thêm Báo thành công!");
                break;
        }
    }

    public void xoaTaiLieu() {
        System.out.println("==== XÓA TÀI LIỆU ====");
        String maXoa = nhapChuoiKhongRong("Nhập mã tài liệu cần xóa (xóa chính xác): ");
        boolean ketQua = danhSach.removeIf(tl -> tl.getMaTaiLieu().equals(maXoa));

        if (ketQua) {
            System.out.println("Đã xóa thành công tài liệu mã: " + maXoa);
        } else {
            System.out.println("Không tìm thấy mã tài liệu này trong hệ thống.");
        }
    }

    public void hienThiThongTin() {
        System.out.println("==== DANH SÁCH TÀI LIỆU ====");
        if (danhSach.isEmpty()) {
            System.out.println("Chưa có tài liệu nào trong hệ thống.");
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
        String tuKhoa = nhapChuoiKhongRong("Nhập từ khóa của mã tài liệu cần tìm: ");
        boolean timThay = false;

        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        for (TaiLieu tl : danhSach) {
            if (tl.getMaTaiLieu().contains(tuKhoa)) {
                if (tl instanceof Sach) {
                    System.out.println("[SÁCH] " + tl.toString());
                } else if (tl instanceof TapChi) {
                    System.out.println("[TẠP CHÍ] " + tl.toString());
                } else if (tl instanceof Bao) {
                    System.out.println("[BÁO] " + tl.toString());
                }
                timThay = true;
            }
        }
        if (!timThay) {
            System.out.println("Không có tài liệu nào chứa mã: " + tuKhoa);
        }
    }
}
