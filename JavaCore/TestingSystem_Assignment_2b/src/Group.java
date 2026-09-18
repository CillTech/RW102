import java.time.LocalDate;

public class Group {
    private int id;
    private String name;
    private Account creator;
    private LocalDate createDate;

    public Group(int id, String groupName, Account creator, LocalDate createDate) {
        this.id = id;
        this.name = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Account getCreator() { return creator; }
    public void setCreator(Account creator) { this.creator = creator; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Group [ID=" + id + ", Name=" + name +
                ", Creator=" + (creator != null ? creator.getFullName() : null) + ", CreateDate=" + createDate + "]";
    }
}