public class Position {
    private int id;
    private PositionName name;

    public Position(int id, PositionName positionName) {
        this.id = id;
        this.name = positionName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public PositionName getName() { return name; }
    public void setName(PositionName name) { this.name = name; }

    @Override
    public String toString() {
        return "Position [ID=" + id + ", Name=" + name + "]";
    }
}