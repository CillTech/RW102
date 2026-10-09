package backend.service.impl;

import backend.repository.IDepartmentRepository;
import backend.repository.impl.DepartmentRepositoryImpl;
import backend.service.IDepartmentService;
import entity.Department;

import java.util.List;

public class DepartmentServiceImpl implements IDepartmentService {
    private IDepartmentRepository repository = new DepartmentRepositoryImpl();

    @Override
    public List<Department> layTatCa() { return repository.layTatCa(); }

    @Override
    public List<Department> timKiemTheoTen(String name) { return repository.timKiemTheoTen(name); }

    @Override
    public boolean themMoi(String name) { return repository.themMoi(name); }

    @Override
    public boolean xoaTheoId(int id) { return repository.xoaTheoId(id); }

    @Override
    public boolean capNhatTenPhongBan(int id, String newName) { return repository.capNhatTenPhongBan(id, newName); }

    @Override
    public boolean isDepartmentIdTonTai(int id) {return repository.isDepartmentIdTonTai(id);};
}