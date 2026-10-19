package backend.service;

import entity.Position;
import java.util.List;

public interface IPositionService {
    List<Position> layTatCa();
    Position layTheoId(int id);
    List<Position> timKiemTheoTen(String name);
    boolean themMoi(String name);
    int themDanhSach(List<String> names);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isPositionIdTonTai(int id);
    boolean isPositionNameTonTai(String name);
}