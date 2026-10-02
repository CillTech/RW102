package entity;

public class Account {
    private int id;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;

    public Account(int id, String email, String username, String fullName, Department department, Position position) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
    }

    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getFullName() { return fullName; }
    public Department getDepartment() { return department; }
    public Position getPosition() { return position; }

    public void setId(int id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setUsername(String username) { this.username = username; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setDepartment(Department department) { this.department = department; }
    public void setPosition(Position position) { this.position = position; }
}