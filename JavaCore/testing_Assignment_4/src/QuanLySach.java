public class QuanLySach {
    private TaiLieu[] danhSach;

    public QuanLySach() {
        // Khởi tạo danh sách giả lập để test do chưa làm chức năng Thêm (1)
        danhSach = new TaiLieu[3];
        danhSach[0] = new Sach("S01", "NXB Kim Dong", 1000, "Nam Cao", 150);
        danhSach[1] = new TapChi("TC01", "NXB Giao Duc", 500, 12, 10);
        danhSach[2] = new Bao("B01", "NXB Tuoi Tre", 2000, 25);
    }

    public void hienThiThongTin() {
        System.out.println("--- DANH SÁCH TÀI LIỆU ---");
        for (TaiLieu tl : danhSach) {
            if (tl != null) {
                System.out.println(tl.toString());
            }
        }
    }

    public void timKiemTheoLoai(int loai) {
        System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
        boolean hasResult = false;
        for (TaiLieu tl : danhSach) {
            if (tl != null) {
                if (loai == 1 && tl instanceof Sach) {
                    System.out.println(tl.toString());
                    hasResult = true;
                } else if (loai == 2 && tl instanceof TapChi) {
                    System.out.println(tl.toString());
                    hasResult = true;
                } else if (loai == 3 && tl instanceof Bao) {
                    System.out.println(tl.toString());
                    hasResult = true;
                }
            }
        }
        if (!hasResult) {
            System.out.println("Không tìm thấy tài liệu phù hợp.");
        }
    }
}