package backend.repository;

import entity.Position;
import java.util.List;

public interface IPositionRepository {
    List<Position> layTatCa();
    List<Position> timKiemTheoTen(String name);
    boolean themMoi(String name);
    boolean xoaTheoId(int id);
    boolean capNhatTen(int id, String newName);
    boolean isPositionIdTonTai(int id);
    boolean isPositionNameTonTai(String name);
}