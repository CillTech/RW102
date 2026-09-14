import java.time.LocalDate;

public class Exam {
    private int id;
    private String code;
    private String title;
    private CategoryQuestion category;
    private int duration;
    private Account creator;
    private LocalDate createDate;

    public Exam(int id, String code, String title, CategoryQuestion category, int duration, Account creator, LocalDate createDate) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.category = category;
        this.duration = duration;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public CategoryQuestion getCategory() { return category; }
    public void setCategory(CategoryQuestion category) { this.category = category; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public Account getCreator() { return creator; }
    public void setCreator(Account creator) { this.creator = creator; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Exam [ID=" + id + ", Code=" + code + ", Title=" + title +
                ", Category=" + (category != null ? category.getName() : null) +
                ", Duration=" + duration + " mins, Creator=" + (creator != null ? creator.getFullName() : null) + "]";
    }
}