package dao;

import entity.Bao;
import entity.Sach;
import entity.TaiLieu;
import entity.TapChi;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaiLieuRepository {

    public boolean themMoi(TaiLieu tl) {
        String sql = "INSERT INTO tai_lieu (ma_tai_lieu, loai_tai_lieu, ten_nha_xuat_ban, so_ban_phat_hanh, " +
                "ten_tac_gia, so_trang, so_phat_hanh, thang_phat_hanh, ngay_phat_hanh) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tl.getMaTaiLieu());
            pstmt.setString(3, tl.getTenNhaXuatBan());
            pstmt.setInt(4, tl.getSoBanPhatHanh());

            // Set NULL mặc định cho các trường riêng
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.INTEGER);
            pstmt.setNull(7, Types.INTEGER);
            pstmt.setNull(8, Types.INTEGER);
            pstmt.setNull(9, Types.INTEGER);

            // Kiểm tra kiểu đối tượng thực tế (Đa hình) để set giá trị phù hợp
            if (tl instanceof Sach) {
                Sach s = (Sach) tl;
                pstmt.setString(2, "SACH");
                pstmt.setString(5, s.getTenTacGia());
                pstmt.setInt(6, s.getSoTrang());
            } else if (tl instanceof TapChi) {
                TapChi tc = (TapChi) tl;
                pstmt.setString(2, "TAP_CHI");
                pstmt.setInt(7, tc.getSoPhatHanh());
                pstmt.setInt(8, tc.getThangPhatHanh());
            } else if (tl instanceof Bao) {
                Bao b = (Bao) tl;
                pstmt.setString(2, "BAO");
                pstmt.setInt(9, b.getNgayPhatHanh());
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaTheoMa(String maTaiLieu) {
        String sql = "DELETE FROM tai_lieu WHERE ma_tai_lieu = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, maTaiLieu);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TaiLieu> layTatCa() {
        return layDuLieu("SELECT * FROM tai_lieu");
    }

    public List<TaiLieu> timKiemGanDung(String tuKhoa) {
        String sql = "SELECT * FROM tai_lieu WHERE ma_tai_lieu LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + tuKhoa + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                return mapResultSetToList(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Hàm dùng chung để chuyển ResultSet thành List<TaiLieu>
    private List<TaiLieu> layDuLieu(String sql) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return mapResultSetToList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<TaiLieu> mapResultSetToList(ResultSet rs) throws SQLException {
        List<TaiLieu> list = new ArrayList<>();
        while (rs.next()) {
            String ma = rs.getString("ma_tai_lieu");
            String loai = rs.getString("loai_tai_lieu");
            String nxb = rs.getString("ten_nha_xuat_ban");
            int soBan = rs.getInt("so_ban_phat_hanh");

            if ("SACH".equals(loai)) {
                String tacGia = rs.getString("ten_tac_gia");
                int soTrang = rs.getInt("so_trang");
                list.add(new Sach(ma, nxb, soBan, tacGia, soTrang));
            } else if ("TAP_CHI".equals(loai)) {
                int soPH = rs.getInt("so_phat_hanh");
                int thangPH = rs.getInt("thang_phat_hanh");
                list.add(new TapChi(ma, nxb, soBan, soPH, thangPH));
            } else if ("BAO".equals(loai)) {
                int ngayPH = rs.getInt("ngay_phat_hanh");
                list.add(new Bao(ma, nxb, soBan, ngayPH));
            }
        }
        return list;
    }
}