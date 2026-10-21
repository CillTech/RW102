package backend.service;

import entity.Department;
import java.util.List;

public interface IDepartmentService {
    List<Department> layTatCa();
    Department layTheoId(int id);
    List<Department> timKiemTheoTen(String name);
    boolean themMoi(String name);
    int themDanhSach(List<String> names);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isDepartmentIdTonTai(int id);
    boolean isDepartmentNameTonTai(String name);

    int importFromCSV(String filePath);
}