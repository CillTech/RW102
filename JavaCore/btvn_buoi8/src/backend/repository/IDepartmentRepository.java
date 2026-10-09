package backend.repository;

import entity.Department;
import java.util.List;

public interface IDepartmentRepository {
    List<Department> layTatCa();
    List<Department> timKiemTheoTen(String name);
    boolean themMoi(String name);
    boolean xoaTheoId(int id);
    boolean capNhatTenPhongBan(int id, String newName);
    boolean isDepartmentIdTonTai(int id);
}