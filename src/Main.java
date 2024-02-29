import javax.management.remote.rmi.RMIServerImpl_Stub;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // a file that contains all students with their current seat number
        File f = new File("Data/students");
        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.exit(1);
        }

        // 2 2DArrays that stores Students and Seats in the setup of the classroom
        Student[][] students = {new Student[12], new Student[6], new Student[6], new Student[2], new Student[4], new Student[4]};
        Seat[][] seats = {new Seat[12], new Seat[6], new Seat[6], new Seat[2], new Seat[4], new Seat[4]};

        // initializing Students in the order of their seat number
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[r].length; c++) {
                String curLine = s.nextLine();
                Student student = new Student(curLine.substring(curLine.indexOf(":") + 2));
                if (!(r == 0 && c == 4)) {
                    students[r][c] = student;
                } else {
                    students[r][c] = null;
                }
            }
        }

        // setting each Student's left and right neighbors according to their index in the array
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[r].length; c++) {
                if (students[r][c] != null) {
                    if (c == 0 && students[r][c + 1] != null) {
                        students[r][c].setOldRNeighbor(students[r][c + 1]);
                    } else if (c == students[r].length - 1 && students[r][c - 1] != null) {
                        students[r][c].setOldLNeighbor(students[r][c - 1]);
                    } else {
                        try {
                            students[r][c].setOldLNeighbor(students[r][c - 1]);
                        } catch (NullPointerException e) {
                            students[r][c].setOldLNeighbor(null);
                        }
                        try {
                            students[r][c].setOldRNeighbor(students[r][c + 1]);
                        } catch (NullPointerException e) {
                            students[r][c].setOldRNeighbor(null);
                        }
                    }
                }
            }
        }

        int seatNum = 1;
        for (int r = 0; r < seats.length; r++) {
            for (int c = 0; c < seats[r].length; c++) {
                if (!(r == 0 && c == 4)) {
                    seats[r][c] = new Seat(seatNum + "", students[r][c]);
                } else {
                    seats[r][c] = new Seat(seatNum + "", null);
                }
                seatNum++;
            }
        }

        boolean isAllSeated = false;
        // assigning Student to Seat according to the layout of the seats
        for (int r = 0; r < students.length; r++) {
            for (int c = 0; c < students[r].length; c++) {
                if (!isAllSeated) {
                    Student currentStudent = students[r][c];

                    // if a Student is currently not seated and has a name
                    if (currentStudent != null && !Objects.equals(currentStudent.getName(), "N/A")) {
                        while (!currentStudent.isSeated()) {
                            // randomized rows according to the amount of seat groups in the classroom
                            int randomRow = (int) (Math.random() * seats.length);
                            // randomized columns according to the amount of seats in a seat group
                            int randomColumn = (int) (Math.random() * seats[randomRow].length);

                            Seat currentSeat = seats[randomRow][randomColumn];

                            // making sure that seat 5 isn't available
                            if (!(randomRow == 0 && randomColumn == 4) && !currentSeat.isTaken()) {
                                // if the Student on the current randomly chosen seat is not the current chosen Student, or the Seat has no one on it
                                if ((currentSeat.getPriviousStudent() != currentStudent) || (currentSeat.getStudent() == null)) {
                                    // if the Student on the left of the current Seat is not the Student's old neighbors, or the left Seat doesn't have anyone on it
                                    if ((currentSeat.getLeftNeighbor() != currentStudent.getOldLNeighbor() && currentSeat.getLeftNeighbor() != currentStudent.getOldRNeighbor()) || currentSeat.getLeftNeighbor() == null) {
                                        //
                                        if ((currentSeat.getRightNeighbor() != currentStudent.getOldLNeighbor() && (currentSeat.getRightNeighbor() != currentStudent.getOldRNeighbor())) || currentSeat.getRightNeighbor() == null) {
                                            currentSeat.setStudent(currentStudent);
                                            seats[randomRow][randomColumn].setTaken(true);
                                            currentStudent.setSeated(true);
                                            if (randomColumn == 0) {
                                                currentSeat.setRightNeighbor(seats[randomRow][randomColumn + 1].getStudent());
                                                seats[randomRow][randomColumn + 1].setLeftNeighbor(currentStudent);
                                            } else if (randomColumn == seats[randomRow].length - 1) {
                                                currentSeat.setLeftNeighbor(seats[randomRow][randomColumn - 1].getStudent());
                                                seats[randomRow][randomColumn - 1].setRightNeighbor(currentStudent);
                                            } else {
                                                try {
                                                    currentSeat.setLeftNeighbor(seats[randomRow][randomColumn - 1].getStudent());
                                                } catch (NullPointerException e) {
                                                    currentSeat.setLeftNeighbor(null);
                                                }
                                                try {
                                                    currentSeat.setRightNeighbor(seats[randomRow][randomColumn + 1].getStudent());
                                                } catch (NullPointerException e) {
                                                    currentSeat.setRightNeighbor(null);
                                                }
                                                seats[randomRow][randomColumn - 1].setRightNeighbor(currentStudent);
                                                seats[randomRow][randomColumn + 1].setLeftNeighbor(currentStudent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    isAllSeated = true;
                    for (int row = 0; row < students.length; row++) {
                        for (int col = 0; col < students[row].length; col++) {
                            if (students[row][col] != null && !students[row][col].isSeated()) {
                                isAllSeated = false;
                                row = students.length - 1;
                                col = students[row].length - 1;
                            }
                        }
                    }

                }
            }
        }

        s.close();

        int n = 1;
        for (Student[] stu: students) {
            for (Student stud: stu) {
                System.out.println(n + ": " + stud);
                n++;
            }
        }

        for (Seat[] st: seats) {
            for (Seat seat: st) {
                System.out.println(seat);
            }
        }
        
    }
}