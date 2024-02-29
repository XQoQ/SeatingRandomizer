public class Seat {
    private String seatNumber;
    private Student student;
    private Student priviousStudent;
    private Student leftNeighbor;
    private Student rightNeighbor;
    private boolean isTaken;

    public Seat(String seatNumber, Student priviousStudent) {
        this.seatNumber = seatNumber;
        this.priviousStudent = priviousStudent;
        this.isTaken = false;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getLeftNeighbor() {
        return leftNeighbor;
    }

    public void setLeftNeighbor(Student leftNeighbor) {
        this.leftNeighbor = leftNeighbor;
    }

    public Student getRightNeighbor() {
        return rightNeighbor;
    }

    public void setRightNeighbor(Student rightNeighbor) {
        this.rightNeighbor = rightNeighbor;
    }

    public Student getPriviousStudent() {
        return priviousStudent;
    }

    public void setPriviousStudent(Student priviousStudent) {
        this.priviousStudent = priviousStudent;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public String toString() {
        String str = seatNumber + ": ";
        if (student != null) {
            str += "The Current Student Assigned Is: " + student.getName() + "\n";
            str += "Previous Left Neighbors was: " + student.getOldLNeighbor() + ", and the current one is: " + getLeftNeighbor() + "\n";
            str += "Previous Right Neighbors was: " + student.getOldRNeighbor() + ", and the current one is: " + getRightNeighbor() + "\n";
        }

        return str;
    }
}