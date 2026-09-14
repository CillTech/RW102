public class Position {
    private int id;
    private PositionName positionName;

    public Position(int id, PositionName positionName) {
        this.id = id;
        this.positionName = positionName;
    }

    public int getid() { return id; }
    public void setid(int id) { this.id = id; }
    public PositionName getPositionName() { return positionName; }
    public void setPositionName(PositionName positionName) { this.positionName = positionName; }

    @Override
    public String toString() {
        return "Position [ID=" + id + ", Name=" + positionName + "]";
    }
}