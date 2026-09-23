public class Department {
    private int id;
    private String name;

    public Department(int id, String departmentName) {
        this.id = id;
        this.name = departmentName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Department [ID=" + id + ", Name=" + name + "]";
    }
}