package backend.controller;

import backend.service.IAccountService;
import backend.service.impl.AccountServiceImpl;
import entity.Account;

import java.util.List;

public class AccountController {
    private IAccountService service = new AccountServiceImpl();

    public List<Account> layTatCa() { return service.layTatCa(); }
    public List<Account> timKiemTheoUsername(String username) { return service.timKiemTheoUsername(username); }
    public boolean themMoi(String email, String username, String fullName, int depId, int posId) {
        return service.themMoi(email, username, fullName, depId, posId);
    }
    public boolean xoaTheoUsername(String username) { return service.xoaTheoUsername(username); }
    public boolean capNhatFullName(String username, String newFullName) { return service.capNhatFullName(username, newFullName); }

    public boolean xoaTheoId(int id) { return service.xoaTheoId(id);}
    public boolean capNhatUsername(int id, String newUsername) { return service.capNhatUsername(id, newUsername); }

    public boolean isEmailTonTai(String email) {return service.isEmailTonTai(email);}
    public boolean isUsernameTonTai(String username) {return service.isUsernameTonTai(username);}
    public boolean isAccountIdTonTai(int id) {return service.isAccountIdTonTai(id);}
}