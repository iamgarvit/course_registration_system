package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Student extends User {
    private int currentSem;
    private int currentSemCredits;
    private int totalCredits;
    private float CGPA;
    private String name;
    private ArrayList<Course> registeredCourses = new ArrayList<>();
    private ArrayList<Course> availableCourses = new ArrayList<>();
    private HashMap<Course, String> completedCourses = new HashMap<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Complaint> submittedComplaints = new ArrayList<>();
    private ArrayList<Feedback> submittedFeedbacks = new ArrayList<>();
    private Scanner sc;
    
    public Student(String username, String password, String Name, int semester) {
		super(username, password);
		this.name = Name;
		this.currentSem = semester;
		this.sc = new Scanner(System.in); 
		students.add(this);
	}


    public Student(String username, String password, Scanner sc) {
		super(username, password);
		this.sc = sc;
		setCurrentSemester();
		setName();
	}
	
	static {
        Student student1 = new Student("garvit@iiitd", "garvit123", "Garvit", 3);
        Student student2 = new Student("shivansh@iiitd", "shivansh123", "Shivansh Singh", 3);
        Student student3 = new Student("tanish@iiitd", "tanish123", "Tanish Bachhas", 1);

        Course cse101 = Course.findCourseByCode("CSE101");
        Course mth100 = Course.findCourseByCode("MTH100");
        Course cse102 = Course.findCourseByCode("CSE102");
        Course cse201 = Course.findCourseByCode("CSE201");
        Course mth201 = Course.findCourseByCode("MTH201");

        student1.completedCourses.put(cse101, "A-");
        student1.completedCourses.put(mth100, "C");
        student1.completedCourses.put(cse102, "B");
        student1.completedCourses.put(mth201, "C");
        student1.registeredCourses.add(cse201);
        cse201.enrollStudentPrePopulate(student1);
        student1.setCGPAPP();
        
        student2.completedCourses.put(cse101, "B");
        student2.completedCourses.put(mth100, "A");
        student2.completedCourses.put(cse101, "B");
        student2.completedCourses.put(cse102, "B-");
        student2.completedCourses.put(mth100, "C");
        student2.completedCourses.put(mth201, "C");
        student2.registeredCourses.add(cse201);
        cse201.enrollStudentPrePopulate(student2);
        student2.setCGPAPP();

        student3.registeredCourses.add(cse101);
        student3.registeredCourses.add(mth100);
        cse101.enrollStudentPrePopulate(student3);
       	mth100.enrollStudentPrePopulate(student3);
    }
    
    public static void initializeStudents(){}
	
	public void setName() {
    	System.out.println("Enter new name : ");
    	String newName = sc.nextLine();
    	this.name = newName;
    }

    public static void signUp(String username, String password, Scanner sc) {
        Student newStudent = new Student(username, password, sc);
        students.add(newStudent);
        System.out.println("Student registered successfully.");
    }

    public static Student findStudent(String username, String password) {
        for (Student student : students) {
            if (student.login(username, password)) {
                return student;
            }
        }
        return null;
    }

    public static void loginStudent(String username, String password, Scanner sc) throws InvalidLoginException {
		Student student = findStudent(username, password);
		if (student != null) {
		    student.sc = sc; 
		    System.out.println("Student logged in as: " + student.username);
		    student.displayMenu();
		} 
		else {
		    throw new InvalidLoginException();
		}
	}

	public static ArrayList<Student> getAllStudents() {
		return students;
	}


    private void displayMenu() {
        while (true) {
            System.out.println("Choose task : " + '\n' +
                               "1. View Available Courses" + '\n' +
                               "2. Register for courses" + '\n' +
                               "3. View schedule" + '\n' +
                               "4. View all grades" + '\n' +
                               "5. View CGPA" + '\n' +
                               "6. Drop course" + '\n' +
                               "7. Update Semester" + '\n' +
                               "8. Submit Complaint" + '\n' +
                               "9. View Complaints" + '\n' +
                               "10. Give feedback for course" + '\n' +
                               "11. View submitted feedback" + '\n' +
                               "12. Logout" + '\n');
            int option = -1;
            if (sc.hasNextInt()) {
                option = sc.nextInt();
                sc.nextLine(); 
            } 
            else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
                continue;
            }

            switch (option) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                	try {
                    	registerForCourses();
                    }
                    catch (CourseFullException e) {}
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                	viewAllGrades();
                	break;
                case 5:
                    viewCGPA();
                    break;
                case 6:
                    dropCourse();
                    break;
                case 7:
                	setCurrentSemester();
                	break;
                case 8:
                    submitComplaint();
                    break;
                case 9:
                	viewComplaint();
                	break;
                case 10:
                	giveFeedback();
                	break;
                case 11:
                	viewFeedback();
                	break;
                case 12:
                    System.out.println("Successfully logged out.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void setAvailableCourses() {
        availableCourses = Course.getAvailableCourses(currentSem);
    }

    private void viewAvailableCourses() {
        setAvailableCourses();
        if (availableCourses.isEmpty()) {
            System.out.println("No courses available.");
        } 
        else {
            for (Course course : availableCourses) {
                course.displayCourseDetails();
            }
        }
    }
    
    public void setCurrentSemester() {
		System.out.println("Enter your current semester: ");
		
		int semester = -1;
		while (true) {
		    if (sc.hasNextInt()) {
		        semester = sc.nextInt();
		        sc.nextLine(); 
		        if (semester > 0 && semester <= 8) {
		            this.currentSem = semester;
		            System.out.println("Current semester set to: " + this.currentSem);
		            break;
		        } 
		        else {
		            System.out.println("Invalid semester. Please enter a valid semester number (1-8).");
		        }
		    } 
		    else {
		        System.out.println("Invalid input. Please enter a number.");
		        sc.nextLine();
		        return;
		    }
		}
	}

	
	public void updateSemester(int newSem){
		this.currentSem = newSem; 
	}
	
	public int getCurrentSemester(){
		return this.currentSem;
	}


    private void registerForCourses() throws CourseFullException {
        System.out.println("Enter course code of the course you wish to register for (press Enter to go back to view courses if needed): ");
        String inputCode = sc.nextLine();
        if (inputCode.equals("")) {
            return;
        }

        for (Course course : availableCourses) {
            if (inputCode.equals(course.getCourseCode())) {
            	if (registeredCourses.contains(course)) {
					System.out.println("You are already registered for this course.");
					return;
				}
                ArrayList<Course> preReqCourses = course.getPreReq();
                if (preReqCourses != null && preReqCourses.size() > 0) {
                    for (Course preReq : preReqCourses) {
                        if (!completedCourses.containsKey(preReq)) {
                            System.out.println("Pre-requisite course incomplete: " + preReq.getCourseCode());
                            return;
                        }
                    }
                }
                if (registeredCourses.contains(course)) {
                    System.out.println("You are already registered for this course.");
                    return;
                }

                if (this.currentSemCredits + course.getCredits() > 20) {
                    System.out.println("Credit limit reached for this semester.");
                    return;
                }
                if (course.isRegistrationFull()) {
                    throw new CourseFullException();
                }

                registeredCourses.add(course);
                course.enrollStudent(this);
                this.currentSemCredits += course.getCredits();
                System.out.println("Registration successful for course: " + course.getCourseCode());
                return;
            }
        }
        System.out.println("Invalid Course Code.");
    }

    private void viewSchedule() {
        if (registeredCourses.isEmpty()) {
            System.out.println("You have not registered for any course.");
        } 
        else {
            for (Course course : registeredCourses) {
                System.out.println(course.getCourseCode() + " : " + course.getTimings());
            }
        }
    }
    
    private void viewAllGrades() {
		if (completedCourses.isEmpty()) {
		    System.out.println("No completed courses found.");
		    return;
		}
		
		for (Course course : completedCourses.keySet()) {
		    if (course != null) {
		        String grade = completedCourses.get(course);
		        System.out.println(course.getCourseCode() + " - " + course.getCourseName() + " : " + grade);
		    } 
		    else {
		        System.out.println("Encountered a null course entry.");
		    }
		}
	}
	
	private void setCGPAPP() {
		if (completedCourses.isEmpty()) {
			return;
		}
		float totalGradePoints = 0;
		int totalCredits = 0;

		for (Course course : completedCourses.keySet()) {
		    String grade = completedCourses.get(course);
		    int gradeValue = getGradeValue(grade);
		    totalGradePoints += gradeValue * course.getCredits();
		    totalCredits += course.getCredits();
		}

		if (totalCredits > 0) {
		    this.CGPA = totalGradePoints / totalCredits;
		} 
		else {
		    this.CGPA = 0;
		}
	}

	private void setCGPA() {
		if (completedCourses.isEmpty()) {
		    System.out.println("No completed courses to calculate CGPA.");
		    return;
		}

		float totalGradePoints = 0;
		int totalCredits = 0;

		for (Course course : completedCourses.keySet()) {
		    String grade = completedCourses.get(course);
		    int gradeValue = getGradeValue(grade);
		    totalGradePoints += gradeValue * course.getCredits();
		    totalCredits += course.getCredits();
		}

		if (totalCredits > 0) {
		    this.CGPA = totalGradePoints / totalCredits;
		} 
		else {
		    this.CGPA = 0;
		}

		System.out.println("CGPA calculated successfully.");
	}

	private int getGradeValue(String grade) {
		switch (grade.toUpperCase()) {
		    case "A+":
		    case "A":
		        return 10;
		    case "A-":
		        return 9;
		    case "B":
		        return 8;
		    case "B-":
		        return 7;
		    case "C":
		        return 6;
		    case "C-":
		        return 5;
		    case "D":
		        return 4;
		    case "F":
		        return 2;
		    default:
		        System.out.println("Invalid grade: " + grade);
		        return 0;
		}
	}

    private void viewCGPA() {
    	this.setCGPA();
        System.out.println("CGPA: " + this.CGPA);
    }

    public float getCGPA() {
        return this.CGPA;
    }
    
    private void viewRegisteredCourses() {
		for (Course course : this.registeredCourses) {
		    System.out.println(course.getCourseCode());
		}
	}

    public void assignGrade() {
		if (registeredCourses.isEmpty()) {
		    System.out.println("The student has no registered courses.");
		    return;
		}

		System.out.println("The registered courses are: ");
		viewRegisteredCourses();
		
		System.out.println("Enter the course code for the desired course to assign grade: ");
		String inputCode = sc.nextLine();

		Course courseToGrade = null;

		for (Course course : registeredCourses) {
		    if (course.getCourseCode().equals(inputCode)) {
		        courseToGrade = course;
		        break;
		    }
		}

		if (courseToGrade != null) {
		    System.out.println("Enter grade for the course " + courseToGrade.getCourseCode() + ": ");
		    String grade = sc.nextLine();
		    
		    completedCourses.put(courseToGrade, grade);
		    
		    registeredCourses.remove(courseToGrade);
		    this.currentSemCredits -= courseToGrade.getCredits(); 
		    
		    System.out.println("Grade " + grade + " has been assigned to the course: " + courseToGrade.getCourseCode());
		} 
		else {
		    System.out.println("Invalid course code or the student is not registered for this course.");
		}
	}

	public void assignGradeNew(Course courseToGrade) {
		String[] validGrades = {"A+", "A", "A-", "B", "B-", "C", "C-", "D", "F"};

		if (!registeredCourses.contains(courseToGrade)) {
		    System.out.println("The student is not registered for the course: " + courseToGrade.getCourseCode());
		    return;
		}

		String grade;
		while (true) {
		    System.out.println("Enter grade for the course " + courseToGrade.getCourseCode() + ": ");
		    grade = sc.nextLine();

		    if (isValidGrade(grade, validGrades)) {
		        break;  
		    } 
		    else {
		        System.out.println("Invalid grade entered. Please enter a valid grade (A+, A, A-, B, B-, C, C-, D, F).");
		    }
		}

		completedCourses.put(courseToGrade, grade);
		registeredCourses.remove(courseToGrade);
		this.currentSemCredits -= courseToGrade.getCredits();

		System.out.println("Grade " + grade + " has been assigned to the course: " + courseToGrade.getCourseCode());
	}

	private boolean isValidGrade(String grade, String[] validGrades) {
		for (String validGrade : validGrades) {
		    if (validGrade.equals(grade)) {
		        return true;
		    }
		}
		return false;
	}

	public void updateGrade() {
		if (completedCourses.isEmpty()) {
		    System.out.println("No completed courses found to update the grade.");
		    return;
		}

		System.out.println("The completed courses are: ");
		for (Course course : completedCourses.keySet()) {
		    System.out.println(course.getCourseCode() + " - " + course.getCourseName());
		}

		System.out.println("Enter the course code for which you want to update the grade: ");
		String inputCode = sc.nextLine();

		Course courseToUpdate = null;
		for (Course course : completedCourses.keySet()) {
		    if (course.getCourseCode().equals(inputCode)) {
		        courseToUpdate = course;
		        break;
		    }
		}

		if (courseToUpdate != null) {
		    System.out.println("Enter the new grade for the course " + courseToUpdate.getCourseCode() + ": ");
		    String newGrade = sc.nextLine();

		    completedCourses.put(courseToUpdate, newGrade);
		    setCGPA();
		    System.out.println("Successfully updated the grade to " + newGrade + " for the course: " + courseToUpdate.getCourseCode());
		} 
		else {
		    System.out.println("Invalid course code or the course is not in the list of completed courses.");
		}
	}


    private void dropCourse() {
        System.out.println("Enter the course code for the course to be dropped: ");
        String inputCode = sc.nextLine();
        Course courseToDrop = null;
        for (Course course : registeredCourses) {
            if (inputCode.equals(course.getCourseCode())) {
                courseToDrop = course;
                break;
            }
        }

        if (courseToDrop != null) {
            registeredCourses.remove(courseToDrop);
            courseToDrop.dropStudent(this);
            this.currentSemCredits -= courseToDrop.getCredits();
            System.out.println("Successfully dropped the course: " + inputCode);
        } 
        else {
            System.out.println("You haven't registered for this course.");
        }
    }
    
    public void setPasswordAdmin(String newPassword) {
    	this.password = newPassword;
    }
    
    public String getusername() {
    	return this.username;
    }
    
    public String getpassword() {
    	return this.password;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public static Student getStudent(String userName) {
		for (Student tempStudent : students) {
		    if (userName.equals(tempStudent.username)) { 
		        return tempStudent;
		    }
		}
		return null;  
	}

    private void submitComplaint() {
        System.out.println("Kindly enter your complaint description : ");
        String description = sc.nextLine();
        
        Complaint newComplaint = new Complaint(this, description);
        this.submittedComplaints.add(newComplaint);
    }
    
    private void viewComplaint() {
    	if (submittedComplaints.isEmpty()){
    		System.out.println("You have not submitted any complaints.");
    		return;
    	}
    	
    	for (Complaint complaint : submittedComplaints){
    		complaint.displayComplaintDetails();
    	}
    }
    
    public boolean hasCourse (Course courseToCheck) {
    	for (Course course : registeredCourses) {
    		if (courseToCheck == course) {
    			return true;
    		} 
    	}
    	return false;
    }
    
    private void giveFeedback() {
    	System.out.println("Enter course code for feedback: ");
    	String courseCodeInput = sc.nextLine();
    	
    	Course feedbackCourse = null;
    	for (Course course : completedCourses.keySet()) {
    		if (courseCodeInput.equals(course.getCourseCode())) {
    			feedbackCourse = course;
    			break;
    		}
    	}
    	
    	if (feedbackCourse == null) {
    		System.out.println("You haven't completed the course: " + courseCodeInput);
    		return;
    	}
    	
    	int rating = -1;
    	while (true) {
			System.out.println("Enter course rating from 1-10: ");
			if (sc.hasNextInt()){
				rating = sc.nextInt();
				sc.nextLine();
			}
			else {
				System.out.println("Please enter a number.");
				continue;
			}
			
			if (rating >= 1 && rating <= 10) {
				break;
			}
			else {
				System.out.println("Please enter a number between 1-10.");
				continue;
			}
    	}
    	
    	System.out.println("Enter comment: ");
    	String comment = sc.nextLine();
    	
    	Feedback<Integer, String> feedback = new Feedback<>(rating, comment, this, feedbackCourse);
    	submittedFeedbacks.add(feedback);
    }
    
    private void viewFeedback() {
    	if (submittedFeedbacks.isEmpty()) {
    		System.out.println("You haven't submitted any feedbacks.");
    		return;
    	}
    	
    	for (Feedback feedback : submittedFeedbacks) {
    		System.out.println("Course: " + feedback.getCourse().getCourseName() + '\n' +
    						   "Rating: " + feedback.getRating() + '\n' +
    						   "Comment: " + feedback.getComment() + '\n');
    	}
    }
}
