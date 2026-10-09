package backend.controller;

import backend.service.IDepartmentService;
import backend.service.impl.DepartmentServiceImpl;
import entity.Department;

import java.util.List;

public class DepartmentController {
    private IDepartmentService service = new DepartmentServiceImpl();

    public List<Department> layTatCa() { return service.layTatCa(); }
    public List<Department> timKiemTheoTen(String name) { return service.timKiemTheoTen(name); }
    public boolean themMoi(String name) { return service.themMoi(name); }
    public boolean xoaTheoId(int id) { return service.xoaTheoId(id); }
    public boolean capNhatTenPhongBan(int id, String newName) { return service.capNhatTenPhongBan(id, newName); }
    public boolean isDepartmentIdTonTai(int id) { return service.isDepartmentIdTonTai(id); };
}