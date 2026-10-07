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
    public boolean themMoi(String email, String username, String fullName, int depId, int posId) {
        return repository.themMoi(email, username, fullName, depId, posId);
    }

    @Override
    public boolean xoaTheoUsername(String username) { return repository.xoaTheoUsername(username); }

    @Override
    public boolean capNhatFullName(String username, String newFullName) { return repository.capNhatFullName(username, newFullName); }
}