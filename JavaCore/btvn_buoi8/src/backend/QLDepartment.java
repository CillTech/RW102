package backend;

import dao.DepartmentRepository;
import entity.Department;
import java.util.List;
import java.util.Scanner;

public class QLDepartment implements IQLDepartment {
    private DepartmentRepository repo = new DepartmentRepository();
    private Scanner sc = new Scanner(System.in);

    @Override
    public void hienThiTatCa() {
        System.out.println("==== DANH SÁCH DEPARTMENT ====");
        inBang(repo.layTatCa());
    }

    @Override
    public void timKiemTheoTen() {
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String name = sc.nextLine();
        List<Department> kq = repo.timKiemTheoTen(name);
        if (kq.isEmpty()) System.out.println("Không tìm thấy!");
        else inBang(kq);
    }

    @Override
    public void themMoi() {
        System.out.print("Nhập tên phòng ban mới: ");
        String name = sc.nextLine();
        if (repo.themMoi(name)) System.out.println("Thêm thành công!");
        else System.out.println("Thêm thất bại (Có thể trùng tên)!");
    }

    @Override
    public void xoaTheoId() {
        System.out.print("Nhập ID phòng ban cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        if (repo.xoaTheoId(id)) System.out.println("Xóa thành công!");
    }

    @Override
    public void capNhatTen() {
        System.out.print("Nhập ID phòng ban cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Nhập tên mới: ");
        String newName = sc.nextLine();
        if (repo.capNhatTenPhongBan(id, newName)) System.out.println("Cập nhật thành công!");
        else System.out.println("Cập nhật thất bại!");
    }

    private void inBang(List<Department> list) {
        System.out.println("+----+-------------------------+");
        System.out.printf("| %-2s | %-23s |\n", "ID", "Department Name");
        System.out.println("+----+-------------------------+");
        for (Department d : list) {
            System.out.printf("| %-2d | %-23s |\n", d.getId(), d.getName());
        }
        System.out.println("+----+-------------------------+");
    }
}