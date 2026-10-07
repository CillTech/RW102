package backend.repository.impl;

import backend.repository.IAccountRepository;
import entity.Account;
import entity.Department;
import entity.Position;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements IAccountRepository {
    @Override
    public List<Account> layTatCa() {
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, p.position_id, p.position_name " +
                "FROM account_table a " +
                "LEFT JOIN department d ON a.department_id = d.department_id " +
                "LEFT JOIN position_table p ON a.position_id = p.position_id";
        return thucThiTruyVan(sql, null);
    }

    @Override
    public List<Account> timKiemTheoUsername(String username) {
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, p.position_id, p.position_name " +
                "FROM account_table a " +
                "LEFT JOIN department d ON a.department_id = d.department_id " +
                "LEFT JOIN position_table p ON a.position_id = p.position_id " +
                "WHERE a.username LIKE ?";
        return thucThiTruyVan(sql, "%" + username + "%");
    }

    @Override
    public boolean themMoi(String email, String username, String fullName, int depId, int posId) {
        String sql = "INSERT INTO account_table (email, username, full_name, department_id, position_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, fullName);
            pstmt.setInt(4, depId);
            pstmt.setInt(5, posId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean xoaTheoUsername(String username) {
        String sql = "DELETE FROM account_table WHERE username = ?";
        return thucThiCapNhat(sql, username);
    }

    @Override
    public boolean capNhatFullName(String username, String newFullName) {
        String sql = "UPDATE account_table SET full_name = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newFullName);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private List<Account> thucThiTruyVan(String sql, String param) {
        List<Account> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (param != null) pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt("department_id"), rs.getString("department_name"));
                    Position pos = new Position(rs.getInt("position_id"), rs.getString("position_name"));
                    list.add(new Account(rs.getInt("account_id"), rs.getString("email"),
                            rs.getString("username"), rs.getString("full_name"), dep, pos));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean thucThiCapNhat(String sql, String param) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, param);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}