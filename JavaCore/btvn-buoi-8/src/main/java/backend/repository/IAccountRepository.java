package backend.repository;

import entity.Account;
import java.util.List;
import java.util.Set;

public interface IAccountRepository {
    List<Account> layTatCa();
    List<Account> timKiemTheoUsername(String username);
    List<Account> timKiemTheoTenSP(String fullName);
    boolean themMoi(Account account);
    int themDanhSach(List<Account> accounts);
    boolean xoaTheoId(int id);
    boolean capNhatUsername(int id, String newUsername);

    boolean isEmailTonTai(String email);
    boolean isUsernameTonTai(String username);
    boolean isUsernameTonTai(String username, int excludeId);
    boolean isAccountIdTonTai(int id);

    int demSoLuong();
    Set<String> layTatCaEmails();
    Set<String> layTatCaUsernames();
}