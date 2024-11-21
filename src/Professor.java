package src;

import java.util.ArrayList;
import java.util.Scanner;

public class Professor extends User {
    private String name;
    private ArrayList<Course> coursesTeaching = new ArrayList<>();
    private static ArrayList<Professor> allProfessors = new ArrayList<>();
    private Scanner sc;

    public Professor(String username, String password, String name) {
        super(username, password);
        this.name = name;
        this.sc = new Scanner(System.in); 
        allProfessors.add(this);
    }

    public String getName() {
        return this.name;
    }

    public static void signUp(String username, String password, Scanner sc) {
        System.out.println("Enter your name: ");
        String name = sc.nextLine();
        Professor newProfessor = new Professor(username, password, name);
        System.out.println("Professor registered successfully.");
    }

    public static Professor findProfessor(String username, String password) {
        for (Professor professor : allProfessors) {
            if (professor.login(username, password)) {
                return professor;
            }
        }
        return null;
    }

    public static void loginProfessor(String username, String password, Scanner sc) throws InvalidLoginException {
        Professor professor = findProfessor(username, password);
        if (professor != null) {
            System.out.println("Professor logged in as: " + professor.username);
            professor.displayMenu();
        } 
        else {
            throw new InvalidLoginException();
        }
    }
    
    public void addCoursesTeachingPP(Course course) {
    	coursesTeaching.add(course);
    }

    private void displayMenu() {
        while (true) {
            System.out.println("Choose task : " + '\n' +
                    		   "1. Manage Courses" + '\n' +
                    		   "2. Manage Students" + '\n' +
                       		   "3. Logout" + '\n');
                       		   
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
                    manageCourses();
                    break;
                case 2:
                    manageStudent();
                    break;
                case 3:
                    System.out.println("Successfully logged out.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    continue;
            }
        }
    }

    private void manageCourses() {
        while (true) {
            System.out.println("Choose task : " + '\n' +
                    		   "1. View Courses" + '\n' +
                    		   "2. Update Courses" + '\n' +
                    	   	   "3. Go back" + '\n');
                    
            int choice = -1;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
            } 
            else {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); 
                continue;
            }

            switch (choice) {
                case 1:
                    viewCourses();
                    break;
                case 2:
                    updateCourses();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void viewCourses() {
    	if(coursesTeaching.isEmpty()){
    		System.out.println("You don't teach any course.");
    		return;
    	}
        for (Course course : coursesTeaching) {
            course.displayCourseDetails();
            System.out.println();
        }
    }

    private void updateCourses() {
        System.out.println("Enter course code you wish to update (Press Enter to go back): ");
        String courseCodeInput = sc.nextLine();
        if (courseCodeInput.equals("")) {
            return;
        }

        Course courseToUpdate = null;
        for (Course course : coursesTeaching) {
            if (courseCodeInput.equals(course.getCourseCode())) {
                courseToUpdate = course;
                break;
            }
        }

        if (courseToUpdate == null) {
            System.out.println("You don't teach this course.");
        } 
        else {
            displayUpdateMenu(courseToUpdate);
        }
    }
    
    public void addCourse(Course courseToAdd) {
		if (this.coursesTeaching.contains(courseToAdd)) {
		    System.out.println("Course is already being taught.");
		} 
		else {
		    this.coursesTeaching.add(courseToAdd);
		    System.out.println("Course added successfully: " + courseToAdd.getCourseCode());
		}
	}

	public void removeCourse(Course courseToRemove) {
		if (this.coursesTeaching.contains(courseToRemove)) {
		    this.coursesTeaching.remove(courseToRemove);
		    System.out.println("Course removed successfully: " + courseToRemove.getCourseCode());
		} 
		else {
		    System.out.println("Course not found in your teaching list.");
		}
	}


    private void displayUpdateMenu(Course tempCourse) {
        System.out.println("Choose what you wish to update: " + '\n' +
                		   "1. Class Timings" + '\n' +
                		   "2. Credits" + '\n' +
                		   "3. Pre-requisites" + '\n' +
                		   "4. Enrollment Limit" + '\n' +
                		   "5. Office Hours" + '\n' +
                		   "6. Assign TA" + '\n' +
                		   "7. Remove TA" + '\n' +
                		   "8. View Feedback" + '\n' +
                		   "9. Go Back" + '\n');

        int choice = -1;
        if (sc.hasNextInt()) {
            choice = sc.nextInt();
            sc.nextLine(); 
        } 
        else {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine(); 
            displayUpdateMenu(tempCourse);
            return;
        }

        switch (choice) {
            case 1:
                System.out.println("Enter new class timings: ");
                String newTimings = sc.nextLine();
                tempCourse.setTimings(newTimings);
                System.out.println("Timings updated to: " + tempCourse.getTimings());
                break;

            case 2:
                System.out.println("Choose new number of credits: " + '\n' +
                				   "1. 2 credits" + '\n' +
                				   "2. 4 credits" + '\n');
                int newCredits = -1;
                if(sc.hasNextInt()){
                	newCredits = sc.nextInt();
                	sc.nextLine();
                }
                else{
                	System.out.println("Invalid input. Choose option 1 or 2.");
                	displayUpdateMenu(tempCourse);
                	return;
                }
                if(newCredits == 1){
                	tempCourse.setCredits(2); 
                }
                else{
                	tempCourse.setCredits(4);
                }
                System.out.println("Credits updated to: " + tempCourse.getCredits());
                break;

            case 3:
                System.out.println("Enter course codes for new pre-requisites, separated by space (or press Enter to skip): ");
                String preReqInput = sc.nextLine();
                String[] preReqCodes = preReqInput.split(" ");

                ArrayList<Course> newPreReqs = new ArrayList<>();
                for (String code : preReqCodes) {
                    Course preReqCourse = Course.findCourseByCode(code);
                    if (preReqCourse != null) {
                        newPreReqs.add(preReqCourse);
                    } 
                    else {
                        System.out.println("Course code " + code + " not found.");
                    }
                }
                tempCourse.setPreReq(newPreReqs);
                System.out.println("Pre-requisites updated.");
                break;

            case 4:
                System.out.println("Enter new enrollment limit: ");
                int newLimit = sc.nextInt();
                sc.nextLine();
                tempCourse.setRegistrationLimit(newLimit);
                System.out.println("Enrollment limit updated to: " + tempCourse.getRegistrationLimit());
                break;

            case 5:
                System.out.println("Enter new office hours: ");
                String newOfficeHours = sc.nextLine();
                tempCourse.setOfficeHours(newOfficeHours);
                System.out.println("Office hours updated to: " + tempCourse.getOfficeHours());
                break;
                
            case 6:
            	assignTAToCourse(tempCourse);
            	break;
            	
            case 7:
            	removeTAFromCourse(tempCourse);
            	break;
            	
            case 8:
            	tempCourse.displayFeedback();
            	break;

            case 9:
                System.out.println("Returning to the previous menu.");
                return;

            default:
                System.out.println("Invalid input.");
                displayUpdateMenu(tempCourse);
                break;
        }
    }

    public static Professor findProfessorByUsername(String username) {
        for (Professor professor : allProfessors) {
            if (professor.getUsername().equals(username)) {
                return professor;
            }
        }
        return null;
    }

	private void manageStudent(){
		while (true){
			System.out.println("Choose task : " + '\n' +
								"1. View enrolled students" + '\n' +
								"2. Assign Grade to student" + '\n' +
								"3. Go back" + '\n');
			int option = -1;
			if(sc.hasNextInt()){
				option = sc.nextInt();
				sc.nextLine();
			}
			else{
				System.out.println("Invalid input. Please enter a number.");
				continue;
			}
			
			switch (option) {
				case 1:
					viewEnrolledStudents();
					break;
				case 2:
					assignGradeToStudent();
					break;
				case 3:
					return;
				case 4:
					System.out.println("Invalid input.");
					return;
			}
		}
	}
	
	private void assignTAToCourse(Course courseToUpdate) {
		System.out.println("Enter the username of TA : ");
		String usernameInput = sc.nextLine();
		TA TAToAssign = TA.findTAByUsername(usernameInput);
		Student studentToUpgrade = Student.getStudent(usernameInput);
		
		if (TAToAssign == null && studentToUpgrade == null) {
			System.out.println("Student with username : " + usernameInput + " does not exist.");
			return;
		}
		
		if (TAToAssign == null) {
			System.out.println("Student with username : " + usernameInput + " found. Upgrade to TA?");
			System.out.println("Enter : " +'\n' + 
							   "1. Yes" + '\n' +
							   "2. No" + '\n');
			int choice = -1;
			while(true) {
				if (sc.hasNextInt()) {
					choice = sc.nextInt();
					sc.nextLine();
					break;
				}
				else {
					System.out.println("Please enter a number." + '\n');
					continue;
				}
			}
			
			switch(choice) {
				case 1:
					TA newTA = new TA(studentToUpgrade.getusername(), studentToUpgrade.getpassword(), studentToUpgrade.getName(), studentToUpgrade.getCurrentSemester());
		            TAToAssign = newTA; 
					break;
				case 2:
					System.out.println("Returning to previous screen.");
					return;
				default:
					System.out.println("Invalid option.");
					return;
			}
		}
		
		TAToAssign.assignTAToCourse(courseToUpdate);
	}
	
	private void removeTAFromCourse(Course courseToUpdate) {
		System.out.println("Enter the username of TA : ");
		String usernameInput = sc.nextLine();
		TA TAToAssign = TA.findTAByUsername(usernameInput);
		
		if (TAToAssign == null) {
			System.out.println("TA with username: " + usernameInput + " does not exist.");
			return;
		}
		
		TAToAssign.removeTAFromCourse(courseToUpdate);
	}
	
	private void assignGradeToStudent() {
		System.out.println("Enter course code for assigning grade : ");
		String courseCodeInput = sc.nextLine();
		
		Course courseToGrade = null;
		for (Course course : coursesTeaching) {
			if (courseCodeInput.equals(course.getCourseCode())) {
				courseToGrade = course;
				break;
			}
		}
		
		if (courseToGrade == null) {
			System.out.println("You don't teach this course.");
			return;
		}
		
		System.out.println("Enter student username to assign grade : ");
		String inputUserName = sc.nextLine();
		Student studentToUpdate = Student.getStudent(inputUserName);
					
		if (studentToUpdate == null) {
			System.out.println("No student found with the username: " + inputUserName);
			return;
		}
		
		if (!studentToUpdate.hasCourse(courseToGrade)) {
			System.out.println("Student is not registered for this course.");
			return;
		}
			
		studentToUpdate.assignGradeNew(courseToGrade);
	}

    private void viewEnrolledStudents() {
        System.out.println("Enter course code for viewing enrolled students: ");
        String inputCourseCode = sc.nextLine();
        for (Course course : coursesTeaching) {
            if (inputCourseCode.equals(course.getCourseCode())) {
            	if(course.getEnrolledStudents().isEmpty()){
            		System.out.println("No students enrolled.");
            		return;
            	}
                for (Student tempStudent : course.getEnrolledStudents()) {
                    System.out.println("Student username: " + tempStudent.getUsername() + '\n' +
                    				   "Student CGPA: " + tempStudent.getCGPA() + '\n');
                }
                return;
            }
        }
        System.out.println("No such course found.");
    }
}
