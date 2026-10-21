package backend.service.impl;

import backend.repository.IAccountRepository;
import backend.repository.IDepartmentRepository;
import backend.repository.IPositionRepository;
import backend.repository.impl.AccountRepositoryImpl;
import backend.repository.impl.DepartmentRepositoryImpl;
import backend.repository.impl.PositionRepositoryImpl;
import backend.service.IAccountService;
import entity.Account;
import entity.Department;
import entity.Position;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AccountServiceImpl implements IAccountService {
    private static final int NGUONG_SO_LUONG = 500000;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private IAccountRepository accRepo = new AccountRepositoryImpl();
    private IDepartmentRepository depRepo = new DepartmentRepositoryImpl();
    private IPositionRepository posRepo = new PositionRepositoryImpl();

    @Override
    public List<Account> layTatCa() { return accRepo.layTatCa(); }

    @Override
    public List<Account> timKiemTheoUsername(String username) { return accRepo.timKiemTheoUsername(username); }

    @Override
    public List<Account> timKiemTheoTenSP(String fullName) { return accRepo.timKiemTheoTenSP(fullName); }

    @Override
    public boolean themMoi(Account account) { return accRepo.themMoi(account); }

    @Override
    public int themDanhSach(List<Account> accounts) { return accRepo.themDanhSach(accounts); }

    @Override
    public boolean xoaTheoId(int id) { return accRepo.xoaTheoId(id); }

    @Override
    public boolean capNhatUsername(int id, String newUsername) { return accRepo.capNhatUsername(id, newUsername); }

    @Override
    public boolean isEmailTonTai(String email) { return accRepo.isEmailTonTai(email); }

    @Override
    public boolean isUsernameTonTai(String username) { return accRepo.isUsernameTonTai(username); }

    @Override
    public boolean isUsernameTonTai(String username, int excludeId) { return accRepo.isUsernameTonTai(username, excludeId); }

    @Override
    public boolean isAccountIdTonTai(int id) { return accRepo.isAccountIdTonTai(id); }

    @Override
    public int importFromCSV(String filePath) {
        int soLuongHienTai = accRepo.demSoLuong();
        boolean dungSet = soLuongHienTai <= NGUONG_SO_LUONG;

        Set<String> setEmails = dungSet ? accRepo.layTatCaEmails() : new HashSet<>();
        Set<String> setUsernames = dungSet ? accRepo.layTatCaUsernames() : new HashSet<>();

        Map<Integer, Department> depMap = depRepo.layTatCa().stream()
                .collect(Collectors.toMap(Department::getId, Function.identity()));

        Map<Integer, Position> posMap = posRepo.layTatCa().stream()
                .collect(Collectors.toMap(Position::getId, Function.identity()));

        List<Account> validList = new ArrayList<>();
        List<String> errorLines = new ArrayList<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
             BufferedReader br = new BufferedReader(new InputStreamReader(bis, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (lineNumber == 1 && line.toLowerCase().startsWith("email")) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                if (parts.length < 5) {
                    errorLines.add(line);
                    continue;
                }

                String email = parts[0].trim();
                String username = parts[1].trim();
                String fullName = parts[2].trim();
                String depIdStr = parts[3].trim();
                String posIdStr = parts[4].trim();

                String cleanLine = email + "," + username + "," + fullName + "," + depIdStr + "," + posIdStr;

                if (email.isEmpty() || username.isEmpty() || fullName.isEmpty()) {
                    errorLines.add(cleanLine);
                    continue;
                }

                if (!EMAIL_PATTERN.matcher(email).matches()) {
                    errorLines.add(cleanLine);
                    continue;
                }

                if (email.length() < 5 || email.length() > 50 ||
                        username.length() < 5 || username.length() > 50 ||
                        fullName.length() < 5 || fullName.length() > 50) {
                    errorLines.add(cleanLine);
                    continue;
                }

                boolean emailTonTai = dungSet ? setEmails.contains(email.toLowerCase())
                        : (setEmails.contains(email.toLowerCase()) || accRepo.isEmailTonTai(email));
                if (emailTonTai) {
                    errorLines.add(cleanLine);
                    continue;
                }

                boolean usernameTonTai = dungSet ? setUsernames.contains(username.toLowerCase())
                        : (setUsernames.contains(username.toLowerCase()) || accRepo.isUsernameTonTai(username));
                if (usernameTonTai) {
                    errorLines.add(cleanLine);
                    continue;
                }

                Integer depId = null;
                Integer posId = null;

                try {
                    if (!depIdStr.isEmpty()) depId = Integer.parseInt(depIdStr);
                } catch (NumberFormatException e) {
                    depId = null;
                }

                try {
                    if (!posIdStr.isEmpty()) posId = Integer.parseInt(posIdStr);
                } catch (NumberFormatException e) {
                    posId = null;
                }

                Department dep = (depId != null) ? depMap.get(depId) : null;
                if (dep == null) {
                    System.out.println("[-] Dòng " + lineNumber + ": Phòng ban của " + fullName + " không tồn tại (sẽ set null).");
                }

                Position pos = (posId != null) ? posMap.get(posId) : null;
                if (pos == null) {
                    System.out.println("[-] Dòng " + lineNumber + ": Vị trí của " + fullName + " không tồn tại (sẽ set null).");
                }

                setEmails.add(email.toLowerCase());
                setUsernames.add(username.toLowerCase());

                Account acc = new Account();
                acc.setEmail(email);
                acc.setUsername(username);
                acc.setFullName(fullName);
                acc.setDepartment(dep);
                acc.setPosition(pos);

                validList.add(acc);
            }

            if (!errorLines.isEmpty()) {
                xuatFileLoi(errorLines, "error_account.csv");
            }

            if (!validList.isEmpty()) {
                return accRepo.themDanhSach(validList);
            }

        } catch (Exception e) {
            System.out.println("[-] Lỗi khi xử lý file CSV: " + e.getMessage());
        }

        return 0;
    }

    private void xuatFileLoi(List<String> errorLines, String errorFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errorFilePath), StandardCharsets.UTF_8))) {
            bw.write("email,username,full_name,department_id,position_id");
            bw.newLine();
            for (String errLine : errorLines) {
                bw.write(errLine);
                bw.newLine();
            }
            System.out.println("[!] Đã phát hiện " + errorLines.size() + " dòng lỗi. Đã xuất ra file: " + errorFilePath);
        } catch (Exception e) {
            System.out.println("[-] Lỗi khi xuất file lỗi CSV: " + e.getMessage());
        }
    }
}