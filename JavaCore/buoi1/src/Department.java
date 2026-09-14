public class Department {
    private int id;
    private String departmentName;

    public Department(int id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    @Override
    public String toString() {
        return "Department [ID=" + id + ", Name=" + departmentName + "]";
    }
}