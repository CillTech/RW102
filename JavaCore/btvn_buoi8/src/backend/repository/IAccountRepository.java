package backend.repository;

import entity.Account;
import java.util.List;

public interface IAccountRepository {
    List<Account> layTatCa();
    List<Account> timKiemTheoUsername(String username);
    boolean themMoi(Account account);
    boolean xoaTheoId(int id);
    boolean capNhatUsername(int id, String newUsername);

    boolean isEmailTonTai(String email);

    // Dành cho Thêm mới (INSERT)
    boolean isUsernameTonTai(String username);

    // Dành cho Cập nhật (UPDATE) - Overloading
    boolean isUsernameTonTai(String username, int excludeId);

    boolean isAccountIdTonTai(int id);
}