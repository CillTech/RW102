package backend.service.impl;

import backend.repository.IDepartmentRepository;
import backend.repository.impl.DepartmentRepositoryImpl;
import backend.service.IDepartmentService;
import entity.Department;

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
import java.util.Set;
import java.util.regex.Pattern;

public class DepartmentServiceImpl implements IDepartmentService {
    private static final int NGUONG_SO_LUONG = 500000;
    private static final String NAME_REGEX = "^[\\p{L}0-9\\s-]{3,50}$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    private IDepartmentRepository repository = new DepartmentRepositoryImpl();

    @Override
    public List<Department> layTatCa() { return repository.layTatCa(); }

    @Override
    public Department layTheoId(int id) { return repository.layTheoId(id); }

    @Override
    public List<Department> timKiemTheoTen(String name) { return repository.timKiemTheoTen(name); }

    @Override
    public boolean themMoi(String name) { return repository.themMoi(name); }

    @Override
    public int themDanhSach(List<String> names) { return repository.themDanhSach(names); }

    @Override
    public boolean xoaTheoId(int id) { return repository.xoaTheoId(id); }

    @Override
    public boolean capNhatTen(int id, String newName) { return repository.capNhatTen(id, newName); }

    @Override
    public boolean isDepartmentIdTonTai(int id) { return repository.isDepartmentIdTonTai(id); }

    @Override
    public boolean isDepartmentNameTonTai(String name) { return repository.isDepartmentNameTonTai(name); }

    @Override
    public int importFromCSV(String filePath) {
        int soLuongHienTai = repository.demSoLuong();
        boolean dungSet = soLuongHienTai <= NGUONG_SO_LUONG;

        Set<String> setNames = dungSet ? repository.layTatCaNames() : new HashSet<>();
        List<String> validList = new ArrayList<>();
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
                if (lineNumber == 1 && line.toLowerCase().startsWith("department_name")) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                String depName = parts[0].trim();

                if (depName.isEmpty()) {
                    errorLines.add(line);
                    continue;
                }

                if (!NAME_PATTERN.matcher(depName).matches()) {
                    errorLines.add(depName);
                    continue;
                }

                boolean daTonTai = dungSet ? setNames.contains(depName.toLowerCase())
                        : (setNames.contains(depName.toLowerCase()) || repository.isDepartmentNameTonTai(depName));

                if (daTonTai) {
                    errorLines.add(depName);
                    continue;
                }

                setNames.add(depName.toLowerCase());
                validList.add(depName);
            }

            if (!errorLines.isEmpty()) {
                xuatFileLoi(errorLines, "error_department.csv");
            }

            if (!validList.isEmpty()) {
                return repository.themDanhSach(validList);
            }

        } catch (Exception e) {
            System.out.println("[-] Lỗi khi xử lý file CSV: " + e.getMessage());
        }

        return 0;
    }

    private void xuatFileLoi(List<String> errorLines, String errorFilePath) {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(errorFilePath), StandardCharsets.UTF_8))) {
            bw.write("department_name");
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