package backend.service.impl;

import backend.repository.IPositionRepository;
import backend.repository.impl.PositionRepositoryImpl;
import backend.service.IPositionService;
import entity.Position;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class PositionServiceImpl implements IPositionService {
    private static final String CHAR_REGEX = "^[\\p{L}0-9\\s-]+$";
    private static final Pattern CHAR_PATTERN = Pattern.compile(CHAR_REGEX);

    private IPositionRepository repository = new PositionRepositoryImpl();

    @Override
    public List<Position> layTatCa() { return repository.layTatCa(); }

    @Override
    public Position layTheoId(int id) { return repository.layTheoId(id); }

    @Override
    public List<Position> timKiemTheoTen(String name) { return repository.timKiemTheoTen(name); }

    @Override
    public boolean themMoi(String name) { return repository.themMoi(name); }

    @Override
    public int themDanhSach(List<String> names) { return repository.themDanhSach(names); }

    @Override
    public boolean xoaTheoId(int id) { return repository.xoaTheoId(id); }

    @Override
    public boolean capNhatTen(int id, String newName) { return repository.capNhatTen(id, newName); }

    @Override
    public boolean isPositionIdTonTai(int id) { return repository.isPositionIdTonTai(id); }

    @Override
    public boolean isPositionNameTonTai(String name) { return repository.isPositionNameTonTai(name); }

    @Override
    public int importFromCSV(String filePath) {
        Set<String> setNames = repository.layTatCaNames();
        Set<String> fileNames = new HashSet<>();
        List<String> validList = new ArrayList<>();
        List<String> errorLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                if (lineNumber == 1 && line.toLowerCase().startsWith("position_name")) {
                    continue;
                }

                String[] parts = line.split(",", -1);
                String posName = parts[0].trim();
                List<String> errors = new ArrayList<>();

                if (posName.isEmpty()) {
                    errors.add("Tên vị trí từ 2-50 kí tự");
                } else {
                    if (posName.length() < 2 || posName.length() > 50) {
                        errors.add("Tên vị trí từ 2-50 kí tự");
                    }
                    if (!CHAR_PATTERN.matcher(posName).matches()) {
                        errors.add("Tên vị trí chỉ được nhập số hoặc chữ");
                    }
                    if (fileNames.contains(posName.toLowerCase())) {
                        errors.add("Tên vị trí trong file bị trùng");
                    } else if (setNames.contains(posName.toLowerCase())) {
                        errors.add("Tên vị trí đã tồn tại");
                    }
                }

                if (!errors.isEmpty()) {
                    errorLines.add(posName + "," + String.join("-", errors));
                    continue;
                }

                fileNames.add(posName.toLowerCase());
                setNames.add(posName.toLowerCase());
                validList.add(posName);
            }

            if (!errorLines.isEmpty()) {
                xuatFileLoi(errorLines, "error_position.csv");
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(errorFilePath, StandardCharsets.UTF_8))) {
            bw.write("position_name,error_messages");
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