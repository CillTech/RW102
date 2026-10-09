package backend.service;

import entity.Department;
import java.util.List;

public interface IDepartmentService {
    List<Department> layTatCa();
    List<Department> timKiemTheoTen(String name);
    boolean themMoi(String name);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isDepartmentIdTonTai(int id);
    boolean isDepartmentNameTonTai(String name);
}