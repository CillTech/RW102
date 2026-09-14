public class TypeQuestion {
    private int id;
    private TypeName typeName;

    public TypeQuestion(int id, TypeName typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public TypeName getTypeName() { return typeName; }
    public void setTypeName(TypeName typeName) { this.typeName = typeName; }

    @Override
    public String toString() {
        return "TypeQuestion [ID=" + id + ", Name=" + typeName + "]";
    }
}