package backend.service.impl;

import backend.repository.IPositionRepository;
import backend.repository.impl.PositionRepositoryImpl;
import backend.service.IPositionService;
import entity.Position;

import java.util.List;

public class PositionServiceImpl implements IPositionService {
    private IPositionRepository repository = new PositionRepositoryImpl();

    @Override
    public List<Position> layTatCa() { return repository.layTatCa(); }

    @Override
    public List<Position> timKiemTheoTen(String name) { return repository.timKiemTheoTen(name); }

    @Override
    public boolean themMoi(String name) { return repository.themMoi(name); }

    @Override
    public boolean xoaTheoId(int id) { return repository.xoaTheoId(id); }

    @Override
    public boolean capNhatTen(int id, String newName) { return repository.capNhatTen(id, newName); }

    @Override
    public boolean isPositionIdTonTai(int id) { return repository.isPositionIdTonTai(id); }

    @Override
    public boolean isPositionNameTonTai(String name) { return repository.isPositionNameTonTai(name); }
}