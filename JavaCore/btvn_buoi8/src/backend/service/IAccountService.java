package backend.service;

import entity.Account;
import java.util.List;

public interface IAccountService {
    List<Account> layTatCa();
    List<Account> timKiemTheoUsername(String username);
    boolean themMoi(String email, String username, String fullName, int depId, int posId);
    boolean xoaTheoUsername(String username);
    boolean capNhatFullName(String username, String newFullName);
}