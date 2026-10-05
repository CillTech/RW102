package dao;

import entity.Department;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    public List<Department> layTatCa() {
        return layDuLieu("SELECT department_id, department_name FROM department", null);
    }

    public List<Department> timKiemTheoTen(String name) {
        return layDuLieu("SELECT department_id, department_name FROM department WHERE department_name LIKE ?", "%" + name + "%");
    }

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

    public boolean xoaTheoId(int id) {
        String sql = "DELETE FROM department WHERE department_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Không thể xóa phòng ban này vì đang có nhân viên phụ thuộc!");
            return false;
        }
    }

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
}