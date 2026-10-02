package backend;

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

public class QuanLyHeThong {

    // 1. Hiển thị toàn bộ account
    public void showAllAccounts() {
        System.out.println("==== DANH SÁCH TOÀN BỘ ACCOUNT ====");
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, p.position_id, p.position_name " +
                "FROM account_table a " +
                "JOIN department d ON a.department_id = d.department_id " +
                "JOIN position_table p ON a.position_id = p.position_id";

        List<Account> accounts = getAccountsFromDB(sql, null);
        printAccountTable(accounts);
    }

    // 2. Tìm kiếm account theo username
    public void searchAccountByUsername(String username) {
        System.out.println("==== KẾT QUẢ TÌM KIẾM ACCOUNT ====");
        String sql = "SELECT a.account_id, a.email, a.username, a.full_name, " +
                "d.department_id, d.department_name, p.position_id, p.position_name " +
                "FROM account_table a " +
                "JOIN department d ON a.department_id = d.department_id " +
                "JOIN position_table p ON a.position_id = p.position_id " +
                "WHERE a.username LIKE ?";

        List<Account> accounts = getAccountsFromDB(sql, "%" + username + "%");
        printAccountTable(accounts);
    }

    // Hàm phụ trợ lấy dữ liệu Account
    private List<Account> getAccountsFromDB(String sql, String param) {
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

    // Hàm in bảng Account
    private void printAccountTable(List<Account> accounts) {
        if (accounts.isEmpty()) {
            System.out.println("Không có dữ liệu.");
            return;
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        System.out.printf("| %-2s | %-25s | %-15s | %-18s | %-23s | %-18s |\n", "ID", "Email", "Username", "Full Name", "Department", "Position");
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        for (Account a : accounts) {
            System.out.printf("| %-2d | %-25s | %-15s | %-18s | %-23s | %-18s |\n",
                    a.getId(), a.getEmail(), a.getUsername(), a.getFullName(), a.getDepartment().getName(), a.getPosition().getName());
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
    }

    // 3. Hiển thị department
    public void showAllDepartments() {
        System.out.println("==== DANH SÁCH DEPARTMENT ====");
        String sql = "SELECT * FROM department";
        List<Department> deps = getDepartmentsFromDB(sql, null);
        printDepartmentTable(deps);
    }

    // 4. Tìm kiếm department theo tên
    public void searchDepartmentByName(String name) {
        System.out.println("==== KẾT QUẢ TÌM KIẾM DEPARTMENT ====");
        String sql = "SELECT * FROM department WHERE department_name LIKE ?";
        List<Department> deps = getDepartmentsFromDB(sql, "%" + name + "%");
        printDepartmentTable(deps);
    }

    // Hàm phụ trợ lấy dữ liệu Department
    private List<Department> getDepartmentsFromDB(String sql, String param) {
        List<Department> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (param != null) pstmt.setString(1, param);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(
                            new Department(
                                    rs.getInt("department_id"),
                                    rs.getString("department_name")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm in bảng Department
    private void printDepartmentTable(List<Department> deps) {
        if (deps.isEmpty()) {
            System.out.println("Không có dữ liệu.");
            return;
        }
        System.out.println("+----+-------------------------+");
        System.out.printf("| %-2s | %-23s |\n", "ID", "Department Name");
        System.out.println("+----+-------------------------+");
        for (Department d : deps) {
            System.out.printf("| %-2d | %-23s |\n", d.getId(), d.getName());
        }
        System.out.println("+----+-------------------------+");
    }
}