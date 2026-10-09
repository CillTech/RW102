package backend.service;

import entity.Account;
import java.util.List;

public interface IAccountService {
    List<Account> layTatCa();
    List<Account> timKiemTheoUsername(String username);
    boolean themMoi(Account account);
    boolean xoaTheoId(int id);
    boolean capNhatUsername(int id, String newUsername);

    boolean isEmailTonTai(String email);
    boolean isUsernameTonTai(String username);
    boolean isUsernameTonTai(String username, int id);
    boolean isAccountIdTonTai(int id);
}