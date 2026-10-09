package backend.controller;

import backend.service.IAccountService;
import backend.service.impl.AccountServiceImpl;
import entity.Account;

import java.util.List;

public class AccountController {
    private IAccountService service = new AccountServiceImpl();

    public List<Account> layTatCa() { return service.layTatCa(); }
    public List<Account> timKiemTheoUsername(String username) { return service.timKiemTheoUsername(username); }
    public boolean themMoi(Account account) { return service.themMoi(account); }
    public boolean xoaTheoId(int id) { return service.xoaTheoId(id); }
    public boolean capNhatUsername(int id, String newUsername) { return service.capNhatUsername(id, newUsername); }

    public boolean isEmailTonTai(String email) { return service.isEmailTonTai(email); }
    public boolean isUsernameTonTai(String username) {
        return service.isUsernameTonTai(username);
    }

    public boolean isUsernameTonTai(String username, int excludeId) {
        return service.isUsernameTonTai(username, excludeId);
    }
    public boolean isAccountIdTonTai(int id) { return service.isAccountIdTonTai(id); }
}