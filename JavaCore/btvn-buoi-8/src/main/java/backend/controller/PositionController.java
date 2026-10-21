package backend.controller;

import backend.service.IPositionService;
import backend.service.impl.PositionServiceImpl;
import entity.Position;

import java.util.List;

public class PositionController {
    private IPositionService service = new PositionServiceImpl();

    public List<Position> layTatCa() { return service.layTatCa(); }
    public Position layTheoId(int id) { return service.layTheoId(id); }
    public List<Position> timKiemTheoTen(String name) { return service.timKiemTheoTen(name); }
    public boolean themMoi(String name) { return service.themMoi(name); }
    public int themDanhSach(List<String> names) { return service.themDanhSach(names); }
    public boolean xoaTheoId(int id) { return service.xoaTheoId(id); }
    public boolean capNhatTen(int id, String newName) { return service.capNhatTen(id, newName); }

    public boolean isPositionIdTonTai(int id) { return service.isPositionIdTonTai(id); }
    public boolean isPositionNameTonTai(String name) { return service.isPositionNameTonTai(name); }

    public int importFromCSV(String filePath) {
        return service.importFromCSV(filePath);
    }
}