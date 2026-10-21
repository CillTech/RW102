package backend.repository;

import entity.Position;
import java.util.List;
import java.util.Set;

public interface IPositionRepository {
    List<Position> layTatCa();
    Position layTheoId(int id);
    List<Position> timKiemTheoTen(String name);
    boolean themMoi(String name);
    int themDanhSach(List<String> names);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isPositionIdTonTai(int id);
    boolean isPositionNameTonTai(String name);

    Set<String> layTatCaNames();
}