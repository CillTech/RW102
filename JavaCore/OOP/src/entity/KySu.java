package entity;

public class KySu extends CanBo {
    private String nganhDaoTao;

    public KySu() {
    }

    public KySu(String hoTen, int tuoi, GioiTinh gioiTinh, String diaChi, Loai loai, String nganhDaoTao) {
        super(hoTen, tuoi, gioiTinh, diaChi, loai);
        this.nganhDaoTao = nganhDaoTao;
    }

    public String getNganhDaoTao() {
        return nganhDaoTao;
    }

    public void setNganhDaoTao(String nganhDaoTao) {
        this.nganhDaoTao = nganhDaoTao;
    }
}
