package backend.repository.impl;

import backend.repository.IPositionRepository;
import entity.Position;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PositionRepositoryImpl implements IPositionRepository {

    @Override
    public List<Position> layTatCa() {
        return layDuLieu("SELECT position_id, position_name FROM position_table", null);
    }

    @Override
    public Position layTheoId(int id) {
        String sql = "SELECT position_id, position_name FROM position_table WHERE position_id = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Position(rs.getInt("position_id"), rs.getString("position_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Position> timKiemTheoTen(String name) {
        return layDuLieu("SELECT position_id, position_name FROM position_table WHERE position_name LIKE ?", "%" + name + "%");
    }

    @Override
    public boolean themMoi(String name) {
        String sql = "INSERT INTO position_table (position_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public int themDanhSach(List<String> names) {
        if (names == null || names.isEmpty()) return 0;

        String sql = "INSERT INTO position_table (position_name) VALUES (?)";
        int count = 0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String name : names) {
                    pstmt.setString(1, name);
                    pstmt.addBatch();
                }

                int[] results = pstmt.executeBatch();
                conn.commit();

                for (int res : results) {
                    if (res >= 0 || res == PreparedStatement.SUCCESS_NO_INFO) {
                        count++;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean xoaTheoId(int id) {
        String sql = "DELETE FROM position_table WHERE position_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean capNhatTen(int id, String newName) {
        String sql = "UPDATE position_table SET position_name = ? WHERE position_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isPositionIdTonTai(int id) {
        String sql = "SELECT 1 FROM position_table WHERE position_id = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isPositionNameTonTai(String name) {
        String sql = "SELECT 1 FROM position_table WHERE position_name = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Set<String> layTatCaNames() {
        Set<String> set = new HashSet<>();
        String sql = "SELECT position_name FROM position_table";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String val = rs.getString("position_name");
                if (val != null) set.add(val.toLowerCase());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return set;
    }

    private List<Position> layDuLieu(String sql, String param) {
        List<Position> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (param != null) pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Position(rs.getInt("position_id"), rs.getString("position_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}