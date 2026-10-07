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
}