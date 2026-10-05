package backend.service.impl;

import backend.repository.ICanBoRepository;
import backend.repository.impl.CanBoRepositoryImpl;
import backend.service.ICanBoService;
import entity.CanBo;

import java.util.List;

public class CanBoServiceImpl implements ICanBoService {

    private ICanBoRepository repository = new CanBoRepositoryImpl();

    @Override
    public boolean themMoi(CanBo cb) {
        return repository.themMoi(cb);
    }

    @Override
    public boolean xoaTheoTen(String ten) {
        return repository.xoaTheoTen(ten);
    }

    @Override
    public List<CanBo> layTatCa() {
        return repository.layTatCa();
    }

    @Override
    public List<CanBo> timKiemTheoTen(String ten) {
        return repository.timKiemTheoTen(ten);
    }

    @Override
    public boolean updateDiaChiTheoTen(String ten, String diaChiMoi) {
        return repository.updateDiaChiTheoTen(ten, diaChiMoi);
    }
}