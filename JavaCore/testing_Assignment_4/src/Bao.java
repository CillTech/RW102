public class Bao extends TaiLieu {
    private int ngayPhatHanh;

    public Bao(String maTaiLieu, String tenNhaXuatBan, int soBanPhatHanh, int ngayPhatHanh) {
        super(maTaiLieu, tenNhaXuatBan, soBanPhatHanh);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public int getNgayPhatHanh() { return ngayPhatHanh; }
    public void setNgayPhatHanh(int ngayPhatHanh) { this.ngayPhatHanh = ngayPhatHanh; }

    @Override
    public String toString() {
        return "[Báo] " + super.toString() + " | Ngày PH: " + ngayPhatHanh;
    }
}