package backend.service.impl;

import backend.repository.IAccountRepository;
import backend.repository.impl.AccountRepositoryImpl;
import backend.service.IAccountService;
import entity.Account;

import java.util.List;

public class AccountServiceImpl implements IAccountService {
    private IAccountRepository repository = new AccountRepositoryImpl();

    @Override
    public List<Account> layTatCa() { return repository.layTatCa(); }

    @Override
    public List<Account> timKiemTheoUsername(String username) { return repository.timKiemTheoUsername(username); }

    @Override
    public boolean themMoi(Account account) { return repository.themMoi(account); }

    @Override
    public boolean xoaTheoId(int id) { return repository.xoaTheoId(id); }

    @Override
    public boolean capNhatUsername(int id, String newUsername) { return repository.capNhatUsername(id, newUsername); }

    @Override
    public boolean isEmailTonTai(String email) { return repository.isEmailTonTai(email); }

    @Override
    public boolean isUsernameTonTai(String username) {
        return repository.isUsernameTonTai(username);
    }

    @Override
    public boolean isUsernameTonTai(String username, int excludeId) {
        return repository.isUsernameTonTai(username, excludeId);
    }

    @Override
    public boolean isAccountIdTonTai(int id) { return repository.isAccountIdTonTai(id); }
}