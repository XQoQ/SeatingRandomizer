public class Student {
    private String name;
    private boolean isSeated;
    private Student oldLNeighbor;
    private Student oldRNeighbor;

    public Student(String name) {
        this.name = name;
        this.isSeated = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSeated() {
        return isSeated;
    }

    public void setSeated(boolean seated) {
        isSeated = seated;
    }

    public Student getOldLNeighbor() {
        return oldLNeighbor;
    }

    public void setOldLNeighbor(Student oldLNeighbor) {
        this.oldLNeighbor = oldLNeighbor;
    }

    public Student getOldRNeighbor() {
        return oldRNeighbor;
    }

    public void setOldRNeighbor(Student oldRNeighbor) {
        this.oldRNeighbor = oldRNeighbor;
    }

    public String toString () {
        String str = name;
        return str;
    }
}