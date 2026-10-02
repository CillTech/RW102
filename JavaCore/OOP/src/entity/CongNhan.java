package entity;

public class CongNhan extends CanBo {
    private int bac;

    public CongNhan() {
    }

    public CongNhan(String hoTen, int tuoi, GioiTinh gioiTinh, String diaChi, Loai loai, int bac) {
        super(hoTen, tuoi, gioiTinh, diaChi, loai);
        this.bac = bac;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }
}
