import java.time.LocalDate;

public class Account {
    private int id;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;
    private LocalDate createDate;

    public Account(int id, String email, String username, String fullName, Department department, Position position, LocalDate createDate) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.department = department;
        this.position = position;
        this.createDate = createDate;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Account [ID=" + id + ", Email=" + email + ", Username=" + username +
                ", FullName=" + fullName + ", Department=" + (department != null ? department.getDepartmentName() : null) +
                ", Position=" + (position != null ? position.getPositionName() : null) + ", CreateDate=" + createDate + "]";
    }
}