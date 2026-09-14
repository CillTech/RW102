public class CategoryQuestion {
    private int id;
    private String categoryName;

    public CategoryQuestion(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    @Override
    public String toString() {
        return "CategoryQuestion [ID=" + id + ", Name=" + categoryName + "]";
    }
}