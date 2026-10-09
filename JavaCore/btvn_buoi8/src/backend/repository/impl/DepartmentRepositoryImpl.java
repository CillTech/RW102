package backend.repository.impl;

import backend.repository.IDepartmentRepository;
import entity.Department;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements IDepartmentRepository {
    @Override
    public List<Department> layTatCa() {
        return layDuLieu("SELECT department_id, department_name FROM department", null);
    }

    @Override
    public List<Department> timKiemTheoTen(String name) {
        return layDuLieu("SELECT department_id, department_name FROM department WHERE department_name LIKE ?", "%" + name + "%");
    }

    @Override
    public boolean themMoi(String name) {
        String sql = "INSERT INTO department (department_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean xoaTheoId(int id) {
        String sql = "DELETE FROM department WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean capNhatTenPhongBan(int id, String newName) {
        String sql = "UPDATE department SET department_name = ? WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private List<Department> layDuLieu(String sql, String param) {
        List<Department> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (param != null) pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Department(rs.getInt("department_id"), rs.getString("department_name")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isDepartmentIdTonTai(int id) {
        String sql = "SELECT 1 FROM department WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) { return false; }
    }
}