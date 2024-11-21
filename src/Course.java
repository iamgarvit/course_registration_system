package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    private int availableInSem;
    private int registrationLimit;
    private int credits;
    private String courseCode;
    private String courseName;
    private String venue;
    private String timings;
    private String officeHours;
    private Professor professor;
    private ArrayList<Course> preReq;
    private ArrayList<Student> enrolledStudents;
    private HashMap<String, Integer> gradeToGP;
    private ArrayList<TA> teachingAssistants = new ArrayList<>();
    private ArrayList<Feedback> courseFeedback = new ArrayList<>();
    private static ArrayList<Course> allCourses = new ArrayList<>();

    public Course(String courseCode, String courseName, int credits, int availableInSem, int registrationLimit, String venue, String timings, Professor professor, String officeHours) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.availableInSem = availableInSem;
        this.registrationLimit = registrationLimit;
        this.venue = venue;
        this.timings = timings;
        this.professor = professor;
        this.officeHours = officeHours;
        this.enrolledStudents = new ArrayList<>();
        this.preReq = new ArrayList<>();
        this.gradeToGP = new HashMap<>();
        allCourses.add(this);
    }
    
    static {
        Professor prof1 = new Professor("pankaj","pankaj123","Mr. Pankaj Jalote");
		Professor prof2 = new Professor("subhajit", "subhajit123", "Mr. Subhajit Ghosechowdhury");

        Course course1 = new Course("CSE101", "Introduction to Programming", 4, 1, 100, "LHC 101", "MW 10:00-11:30", prof1, "F 2:00-4:00");
        Course course2 = new Course("CSE102", "Data Structures & Algorithms", 4, 2, 100, "LHC 102", "TTh 11:00-12:30", prof1, "M 2:00-4:00");
        Course course3 = new Course("MTH100", "Linear Algebra", 4, 1, 100, "LHC 201", "MWF 09:30-10:30", prof2, "Th 10:00-12:00");
        Course course4 = new Course("CSE201", "Advanced Programming", 4, 3, 100, "B-003", "TTh 10:00-11:30", prof1, "F 4:00-5:00");
        Course course5 = new Course("MTH201", "Probability and Statistics", 4, 2, 100, "LHC 201", "TTh 12:00-1:30", prof2, "F 9:00-11:00");
        
        prof1.addCoursesTeachingPP(course1);
        prof1.addCoursesTeachingPP(course2);
        prof1.addCoursesTeachingPP(course4);
        prof2.addCoursesTeachingPP(course3);
        prof2.addCoursesTeachingPP(course5);
        
        course5.preReq.add(course3);
        course4.preReq.add(course1);
        course4.preReq.add(course2);
    }

	public static void initializeCourses() {}

    public String getCourseCode() {
        return this.courseCode;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public String getVenue() {
        return this.venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTimings() {
        return this.timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public Professor getProfessor() {
        return this.professor;
    }
    
    public void setProfessor(Professor newProfessor) {
    	this.professor = newProfessor;
    }

    public ArrayList<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return this.credits;
    }

    public void setRegistrationLimit(int limit) {
        this.registrationLimit = limit;
    }

    public int getRegistrationLimit() {
        return this.registrationLimit;
    }

    public boolean isRegistrationFull() {
        return this.enrolledStudents.size() >= this.registrationLimit;
    }

    public ArrayList<Course> getPreReq() {
        return this.preReq;
    }

    public void setPreReq(ArrayList<Course> newPreReq) {
        this.preReq = newPreReq;
    }
    
    public static ArrayList<Course> getAllCourses() {
		return allCourses;
	}
	
	public String getOfficeHours() {
    	return this.officeHours;
    }
    
    public void setOfficeHours(String newOfficeHours) {
    	this.officeHours = newOfficeHours;
    }

	public int getSemester() {
        return this.availableInSem;
    }

	public void addTAToCourse(TA taToAdd) {
		this.teachingAssistants.add(taToAdd);
	}
	
	public void removeTAFromCourse(TA taToRemove) {
		this.teachingAssistants.remove(taToRemove);
	}

	public static void displayAllCourses() {
		ArrayList<Course> coursesToDisplay = getAllCourses();
		if (coursesToDisplay.isEmpty()) {
		    System.out.println("No courses available.");
		} 
		else {
		    System.out.println("List of available courses:");
		    for (Course course : coursesToDisplay) {
		        System.out.println("Course Code: " + course.getCourseCode() + '\n' +
		                           "Course Name: " + course.getCourseName() + '\n' +
		                           "Credits: " + course.getCredits() + '\n' +
		                           "Professor: " + course.getProfessor().getName());
		    }
		}
	}
	
	public void enrollStudentPrePopulate(Student student){
		enrolledStudents.add(student);
	}

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            System.out.println("Student " + student.getUsername() + " enrolled in course " + courseCode);
        } 
        else {
            System.out.println("Student already enrolled in this course.");
        }
    }

    public void dropStudent(Student student) {
        if (enrolledStudents.contains(student)) {
            enrolledStudents.remove(student);
            System.out.println("Student " + student.getUsername() + " dropped from course " + courseCode);
        } 
        else {
            System.out.println("Student is not enrolled in this course.");
        }
    }
    
    public static ArrayList<Course> getAvailableCourses(int currentSem) {
        ArrayList<Course> availableCourses = new ArrayList<>();

        for (Course course : allCourses) {
            if (course.availableInSem == currentSem) {
                availableCourses.add(course);
            }
        }
        return availableCourses;
    }

    public void displayCourseDetails() {
        System.out.println("Course Code: " + courseCode);
        System.out.println("Course Name: " + courseName);
        System.out.println("Credits: " + credits);
        System.out.println("Venue: " + venue);
        System.out.println("Timings: " + timings);
        System.out.println("Professor: " + professor.getName());
        System.out.println("Number of Enrolled Students: " + enrolledStudents.size());
        System.out.println("Prerequisites: ");
        if (preReq.isEmpty()) {
            System.out.println("None");
        } 
        else {
            for (Course course : preReq) {
                System.out.print(course.getCourseCode() + " ");
            }
            System.out.println();
        }
    }
    
     public static Course findCourseByCode(String courseCode) {
        for (Course course : allCourses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    public void addFeedback(Feedback feedback) {
    	courseFeedback.add(feedback);
    }
    
    public void displayFeedback() {
    	if (courseFeedback.isEmpty()) {
    		System.out.println("No feedback for course: " + courseCode);
    		return;
    	}
    
    	for (Feedback feedback : courseFeedback) {
    		System.out.println("Student username: " + feedback.getStudentSubmit().getusername() + '\n' +
    						   "Rating: " + feedback.getRating() + '\n' +
    						   "Comment: " + feedback.getComment() + '\n');
    	}
    }
}
