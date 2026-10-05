package backend;

import dao.AccountRepository;
import entity.Account;
import java.util.List;
import java.util.Scanner;

public class QLAccount implements IQLAccount {
    private AccountRepository repo = new AccountRepository();
    private Scanner sc = new Scanner(System.in);

    @Override
    public void hienThiTatCa() {
        System.out.println("==== DANH SÁCH ACCOUNT ====");
        inBang(repo.layTatCa());
    }

    @Override
    public void timKiemTheoUsername() {
        System.out.print("Nhập username cần tìm: ");
        String username = sc.nextLine();
        List<Account> kq = repo.timKiemTheoUsername(username);
        if (kq.isEmpty()) System.out.println("Không tìm thấy!");
        else inBang(kq);
    }

    @Override
    public void themMoi() {
        System.out.println("==== THÊM MỚI ACCOUNT ====");
        System.out.print("Nhập Email: "); String email = sc.nextLine();
        System.out.print("Nhập Username: "); String username = sc.nextLine();
        System.out.print("Nhập Full Name: "); String fullName = sc.nextLine();
        System.out.print("Nhập ID Phòng ban (1-10): "); int depId = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập ID Vị trí (1-4): "); int posId = Integer.parseInt(sc.nextLine());

        if (repo.themMoi(email, username, fullName, depId, posId)) {
            System.out.println("Thêm thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    @Override
    public void xoaTheoUsername() {
        System.out.print("Nhập username cần xóa chính xác: ");
        String username = sc.nextLine();
        if (repo.xoaTheoUsername(username)) {
            System.out.println("Xóa thành công!");
        } else {
            System.out.println("Không tìm thấy account hoặc lỗi khi xóa.");
        }
    }

    @Override
    public void capNhatFullName() {
        System.out.print("Nhập username cần cập nhật: ");
        String username = sc.nextLine();
        System.out.print("Nhập Full Name mới: ");
        String newName = sc.nextLine();

        if (repo.capNhatFullName(username, newName)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    private void inBang(List<Account> list) {
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        System.out.printf("| %-2s | %-25s | %-15s | %-18s | %-23s | %-18s |\n", "ID", "Email", "Username", "Full Name", "Department", "Position");
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
        for (Account a : list) {
            String depName = (a.getDepartment().getName() != null) ? a.getDepartment().getName() : "Chưa có";
            String posName = (a.getPosition().getName() != null) ? a.getPosition().getName() : "Chưa có";
            System.out.printf("| %-2d | %-25s | %-15s | %-18s | %-23s | %-18s |\n",
                    a.getId(), a.getEmail(), a.getUsername(), a.getFullName(), depName, posName);
        }
        System.out.println("+----+---------------------------+-----------------+--------------------+-------------------------+--------------------+");
    }
}