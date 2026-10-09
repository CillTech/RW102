package backend.repository;

import entity.Account;
import java.util.List;

public interface IAccountRepository {
    List<Account> layTatCa();
    List<Account> timKiemTheoUsername(String username);
    boolean themMoi(String email, String username, String fullName, int depId, int posId);
    boolean xoaTheoUsername(String username);
    boolean capNhatFullName(String username, String newFullName);
    boolean xoaTheoId(int id);
    boolean capNhatUsername(int id, String newUsername);
    boolean isEmailTonTai(String email);
    boolean isUsernameTonTai(String username);
    boolean isAccountIdTonTai(int id);
}