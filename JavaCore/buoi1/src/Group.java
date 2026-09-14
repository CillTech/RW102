import java.time.LocalDate;

public class Group {
    private int id;
    private String groupName;
    private Account creator;
    private LocalDate createDate;

    public Group(int id, String groupName, Account creator, LocalDate createDate) {
        this.id = id;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public Account getCreator() { return creator; }
    public void setCreator(Account creator) { this.creator = creator; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Group [ID=" + id + ", Name=" + groupName +
                ", Creator=" + (creator != null ? creator.getFullName() : null) + ", CreateDate=" + createDate + "]";
    }
}