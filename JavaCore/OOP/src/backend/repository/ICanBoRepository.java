package backend.repository;

import entity.CanBo;
import java.util.List;

public interface ICanBoRepository {
    boolean themMoi(CanBo cb);
    boolean xoaTheoTen(String ten);
    List<CanBo> layTatCa();
    List<CanBo> timKiemTheoTen(String ten);
    boolean updateDiaChiTheoTen(String ten, String diaChiMoi);
}