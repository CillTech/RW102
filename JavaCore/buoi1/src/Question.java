import java.time.LocalDate;

public class Question {
    private int questionId;
    private String content;
    private CategoryQuestion category;
    private TypeQuestion type;
    private Account creator;
    private LocalDate createDate;

    public Question(int questionId, String content, CategoryQuestion category, TypeQuestion type, Account creator, LocalDate createDate) {
        this.questionId = questionId;
        this.content = content;
        this.category = category;
        this.type = type;
        this.creator = creator;
        this.createDate = createDate;
    }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public CategoryQuestion getCategory() { return category; }
    public void setCategory(CategoryQuestion category) { this.category = category; }
    public TypeQuestion getType() { return type; }
    public void setType(TypeQuestion type) { this.type = type; }
    public Account getCreator() { return creator; }
    public void setCreator(Account creator) { this.creator = creator; }
    public LocalDate getCreateDate() { return createDate; }
    public void setCreateDate(LocalDate createDate) { this.createDate = createDate; }

    @Override
    public String toString() {
        return "Question [ID=" + questionId + ", Content=" + content +
                ", Category=" + (category != null ? category.getCategoryName() : null) +
                ", Creator=" + (creator != null ? creator.getFullName() : null) + "]";
    }
}