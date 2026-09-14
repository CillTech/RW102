import java.time.LocalDate;

public class Group {
    private int groupId;
    private String groupName;
    private Account creator;
    private LocalDate createDate;

    public Group(int groupId, String groupName, Account creator, LocalDate createDate) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getGroupId() { return groupId; }
    public void setGroupId(int groupId) { this.groupId = groupId; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public Account getCreator() { return creator; }
    public void setCreator(Account creator) { this.creator = creator; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Group [ID=" + groupId + ", Name=" + groupName +
                ", Creator=" + (creator != null ? creator.getFullName() : null) + ", CreateDate=" + createDate + "]";
    }
}