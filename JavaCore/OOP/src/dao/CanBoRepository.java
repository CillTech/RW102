package dao;

import entity.*;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CanBoRepository {

    public boolean themMoi(CanBo cb) {
        String sql = "INSERT INTO can_bo (ho_ten, tuoi, gioi_tinh, dia_chi, loai, bac, nganh, cong_viec) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cb.getHoTen());
            pstmt.setInt(2, cb.getTuoi());
            pstmt.setString(3, cb.getGioiTinh().name());
            pstmt.setString(4, cb.getDiaChi());

            // Set NULL mặc định cho các trường riêng
            pstmt.setNull(6, Types.INTEGER);
            pstmt.setNull(7, Types.VARCHAR);
            pstmt.setNull(8, Types.VARCHAR);

            // Kiểm tra kiểu đối tượng thực tế (Đa hình) để set giá trị phù hợp
            if (cb instanceof CongNhan) {
                CongNhan cn = (CongNhan) cb;
                pstmt.setString(5, "CN");
                pstmt.setInt(6, cn.getBac());
            } else if (cb instanceof KySu) {
                KySu ks = (KySu) cb;
                pstmt.setString(5, "KS");
                pstmt.setString(7, ks.getNganhDaoTao());
            } else if (cb instanceof NhanVien) {
                NhanVien nv = (NhanVien) cb;
                pstmt.setString(5, "NV");
                pstmt.setString(8, nv.getCongViec());
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaTheoTen(String ten) {
        String sql = "DELETE FROM can_bo WHERE ho_ten = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, ten);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CanBo> layTatCa() {
        return layDuLieu("SELECT * FROM can_bo", null);
    }

    public List<CanBo> timKiemTheoTen(String ten) {
        return layDuLieu("SELECT * FROM can_bo WHERE ho_ten LIKE ?", ten);
    }

    // Hàm dùng chung để lấy dữ liệu
    private List<CanBo> layDuLieu(String sql, String tuKhoa) {
        List<CanBo> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (tuKhoa != null) {
                pstmt.setString(1, "%" + tuKhoa + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String hoTen = rs.getString("ho_ten");
                    int tuoi = rs.getInt("tuoi");
                    GioiTinh gt = GioiTinh.valueOf(rs.getString("gioi_tinh"));
                    String diaChi = rs.getString("dia_chi");
                    String loai = rs.getString("loai");

                    if ("CN".equals(loai)) {
                        int bac = rs.getInt("bac");
                        list.add(new CongNhan(hoTen, tuoi, gt, diaChi, Loai.CN, bac));
                    } else if ("KS".equals(loai)) {
                        String nganh = rs.getString("nganh");
                        list.add(new KySu(hoTen, tuoi, gt, diaChi, Loai.KS, nganh));
                    } else if ("NV".equals(loai)) {
                        String congViec = rs.getString("cong_viec");
                        list.add(new NhanVien(hoTen, tuoi, gt, diaChi, Loai.NV, congViec));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}