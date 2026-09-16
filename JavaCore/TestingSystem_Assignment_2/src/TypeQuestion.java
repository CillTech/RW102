public class TypeQuestion {
    private int id;
    private TypeName name;

    public TypeQuestion(int id, TypeName typeName) {
        this.id = id;
        this.name = typeName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public TypeName getName() { return name; }
    public void setName(TypeName name) { this.name = name; }

    @Override
    public String toString() {
        return "TypeQuestion [ID=" + id + ", Name=" + name + "]";
    }
}