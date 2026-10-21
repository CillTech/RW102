package backend.repository;

import entity.Department;
import java.util.List;
import java.util.Set;

public interface IDepartmentRepository {
    List<Department> layTatCa();
    Department layTheoId(int id);
    List<Department> timKiemTheoTen(String name);
    boolean themMoi(String name);
    int themDanhSach(List<String> names);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isDepartmentIdTonTai(int id);
    boolean isDepartmentNameTonTai(String name);

    int demSoLuong();
    Set<String> layTatCaNames();
}