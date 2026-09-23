public class CategoryQuestion {
    private int id;
    private String name;

    public CategoryQuestion(int id, String categoryName) {
        this.id = id;
        this.name = categoryName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "CategoryQuestion [ID=" + id + ", Name=" + name + "]";
    }
}