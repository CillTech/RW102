package backend.controller;

import backend.service.ICanBoService;
import backend.service.impl.CanBoServiceImpl;
import entity.CanBo;

import java.util.List;

public class CanBoController {

    private ICanBoService service = new CanBoServiceImpl();

    public boolean themMoi(CanBo cb) {
        return service.themMoi(cb);
    }

    public boolean xoaTheoTen(String ten) {
        return service.xoaTheoTen(ten);
    }

    public List<CanBo> layTatCa() {
        return service.layTatCa();
    }

    public List<CanBo> timKiemTheoTen(String ten) {
        return service.timKiemTheoTen(ten);
    }

    public boolean updateDiaChiTheoTen(String ten, String diaChiMoi) {
        return service.updateDiaChiTheoTen(ten, diaChiMoi);
    }
}