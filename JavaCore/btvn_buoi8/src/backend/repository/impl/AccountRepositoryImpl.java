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
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryImpl implements IAccountRepository {

    @Override
    public List<Account> layTatCa() {
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, " +
                "p.position_id, p.position_name " +
                "FROM account_table a " +
                "LEFT JOIN department d ON a.department_id = d.department_id " +
                "LEFT JOIN position_table p ON a.position_id = p.position_id";
        return layDanhSach(sql, null);
    }

    @Override
    public List<Account> timKiemTheoUsername(String username) {
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, " +
                "p.position_id, p.position_name " +
                "FROM account_table a " +
                "LEFT JOIN department d ON a.department_id = d.department_id " +
                "LEFT JOIN position_table p ON a.position_id = p.position_id " +
                "WHERE a.username LIKE ?";
        return layDanhSach(sql, "%" + username + "%");
    }

    @Override
    public boolean themMoi(Account account) {
        String sql = "INSERT INTO account_table (email, username, full_name, department_id, position_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, account.getEmail());
            pstmt.setString(2, account.getUsername());
            pstmt.setString(3, account.getFullName());

            if (account.getDepartment() != null) {
                pstmt.setInt(4, account.getDepartment().getId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            if (account.getPosition() != null) {
                pstmt.setInt(5, account.getPosition().getId());
            } else {
                pstmt.setNull(5, Types.INTEGER);
            }

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean xoaTheoId(int id) {
        String sql = "DELETE FROM account_table WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean capNhatUsername(int id, String newUsername) {
        String sql = "UPDATE account_table SET username = ? WHERE account_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newUsername);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isEmailTonTai(String email) {
        String sql = "SELECT 1 FROM account_table WHERE email = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isUsernameTonTai(String username) {
        return isUsernameTonTai(username, -1);
    }

    @Override
    public boolean isUsernameTonTai(String username, int excludeId) {
        String sql = "SELECT 1 FROM account_table WHERE username = ? AND account_id != ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, excludeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean isAccountIdTonTai(int id) {
        String sql = "SELECT 1 FROM account_table WHERE account_id = ? LIMIT 1";
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

    private List<Account> layDanhSach(String sql, String param) {
        List<Account> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (param != null) pstmt.setString(1, param);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Department dep = new Department(rs.getInt("department_id"), rs.getString("department_name"));
                    Position pos = new Position(rs.getInt("position_id"), rs.getString("position_name"));
                    Account acc = new Account(
                            rs.getInt("account_id"),
                            rs.getString("email"),
                            rs.getString("username"),
                            rs.getString("full_name"),
                            dep,
                            pos
                    );
                    list.add(acc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}