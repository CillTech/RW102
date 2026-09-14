public class TypeQuestion {
    private int typeId;
    private TypeName typeName;

    public TypeQuestion(int typeId, TypeName typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() { return typeId; }
    public void setTypeId(int typeId) { this.typeId = typeId; }
    public TypeName getTypeName() { return typeName; }
    public void setTypeName(TypeName typeName) { this.typeName = typeName; }

    @Override
    public String toString() {
        return "TypeQuestion [ID=" + typeId + ", Name=" + typeName + "]";
    }
}