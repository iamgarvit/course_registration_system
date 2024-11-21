package src;

import java.util.ArrayList;
import java.util.Scanner;

public class TA extends Student {
    private ArrayList<Course> assignedCourses = new ArrayList<>();
    private static ArrayList<TA> allTAs = new ArrayList<>();

    public TA(String username, String password, String name, int semester) {
        super(username, password, name, semester);
        allTAs.add(this);
    }

    public static TA findTA(String username, String password) {
        for (TA ta : allTAs) {
            if (ta.login(username, password)) {
                return ta;
            }
        }
        return null;
    }
    
    public static void loginTA(String username, String password, Scanner sc) throws InvalidLoginException {
        TA ta = findTA(username, password);
        if (ta != null) {
            System.out.println("TA logged in successfully as: " + ta.username);
            ta.displayMenuTA(sc);
        } 
        else {
            throw new InvalidLoginException();
        }
    }

    private void displayMenuTA(Scanner sc) {
        while (true) {
            System.out.println("Choose task: " + '\n' +
                    "1. View Courses" + '\n' +
                    "2. Assign student grade" + '\n' +
                    "3. Logout" + '\n');
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    assignGradeToStudent(sc);
                    break;
                case 3:
                    System.out.println("Successfully logged out.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    public void assignTAToCourse(Course course) {
        if (assignedCourses.contains(course)) {
            System.out.println("TA: " + this.username + " is already assigned to course: " + course.getCourseCode());
            return;
        }
        assignedCourses.add(course);
        course.addTAToCourse(this);
        System.out.println("TA: " + this.username + " has been assigned to course: " + course.getCourseCode());
    }

    public static TA findTAByUsername(String username) {
        for (TA ta : allTAs) {
            if (ta.username.equals(username)) {
                return ta;
            }
        }
        return null;
    }

    private void viewCourses() {
        if (assignedCourses.isEmpty()) {
            System.out.println("No assigned courses as TA.");
            return;
        }

        for (Course course : assignedCourses) {
            System.out.println("Course Code: " + course.getCourseCode() + '\n' +
                    "Course Name: " + course.getCourseName() + '\n' +
                    "Professor: " + course.getProfessor().getName() + '\n');
        }
    }

    private void assignGradeToStudent(Scanner sc) {
        System.out.println("Enter course code to assign grade:");
        String courseCodeInput = sc.nextLine();

        Course courseToGrade = null;
        for (Course course : assignedCourses) {
            if (courseCodeInput.equals(course.getCourseCode())) {
                courseToGrade = course;
                break;
            }
        }

        if (courseToGrade == null) {
            System.out.println("You are not a TA for this course.");
            return;
        }

        System.out.println("Enter student username to assign grade:");
        String inputUserName = sc.nextLine();
        Student studentToUpdate = Student.getStudent(inputUserName);

        if (studentToUpdate == null) {
            System.out.println("No student found with username: " + inputUserName);
            return;
        }

        if (!studentToUpdate.hasCourse(courseToGrade)) {
            System.out.println("Student is not registered for this course.");
            return;
        }

        studentToUpdate.assignGradeNew(courseToGrade); 
    }

    public void removeTAFromCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            System.out.println("TA: " + this.username + " is not assigned to course: " + course.getCourseCode());
            return;
        }

        assignedCourses.remove(course);
        course.removeTAFromCourse(this);
        System.out.println("TA: " + this.username + " has been removed from course: " + course.getCourseCode());
    }
}
