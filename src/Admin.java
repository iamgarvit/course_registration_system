package src;

import java.util.Scanner;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public static void loginAdmin(String username, String password, Scanner sc) throws InvalidLoginException {
        String adminUsername = "admin";
        String adminPassword = "admin123";

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Admin logged in successfully.");
            displayAdminMenu(sc);
        } 
        else {
            throw new InvalidLoginException();
        }
    }

    private static void displayAdminMenu(Scanner sc) {
        while (true) {
            System.out.println("Admin Menu: " + '\n' +
                               "1. Manage Students" + '\n' +
                               "2. Manage Professors" + '\n' +
                               "3. Manage Courses" + '\n' +
                               "4. Manage Complaints" + '\n' +
                               "5. Logout" + '\n');
            
            int option = sc.nextInt();
            sc.nextLine(); 

            switch (option) {
                case 1:
                    manageStudents(sc);
                    break;
                case 2:
                    manageProfessors(sc); 
                    break;
                case 3:
                    manageCourses(sc);
                    break;
                case 4:
                    manageComplaints(sc);
                    break;
                case 5:
                    System.out.println("Successfully logged out.");
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private static void manageStudents(Scanner sc) {
        int option = -1;
        System.out.println("Choose option : " + '\n' +
        				   "1. View all students" + '\n' +
                           "2. Update student records" + '\n' +
                           "3. Go back" + '\n');
        
        if (sc.hasNextInt()) {
            option = sc.nextInt();
            sc.nextLine();
        } 
        else {
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine(); 
        }
        
        switch (option) {
            case 1:
                viewAllStudents();
                break;
            case 2:
                updateStudent(sc);
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid option.");
                break;
        }
    }

    private static void viewAllStudents() {
        System.out.println("All students are:");
        for (Student student : Student.getAllStudents()) {
            System.out.println("Student: " + student.getusername());
        }
    }

    private static void updateStudent(Scanner sc) {
        System.out.println("Enter student username to update: ");
        String inputUserName = sc.nextLine();
        Student studentToUpdate = Student.getStudent(inputUserName);
        
        if (studentToUpdate == null) {
            System.out.println("No student found with the username: " + inputUserName);
            return;
        }
        System.out.println("Student found: " + studentToUpdate.getUsername());

        while (true) {
            int choice = -1;
            System.out.println("Choose option : " + '\n' +
            				   "1. Update Name" + '\n' +
            				   "2. Update Password" + '\n' +
                               "3. Assign Grade" + '\n' +
                               "4. Update Grade" + '\n' +
                               "5. Update Semester" + '\n' +
                               "6. Go back" + '\n');
            
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();  
            } 
            else {
                System.out.println("Invalid input. Please enter an integer.");
                sc.nextLine();  
                continue;
            }
            
            switch (choice) {
                case 1:
                    studentToUpdate.setName();
                    break;
                case 2:
                	System.out.println("Enter new password : ");
                	String newPass = sc.nextLine();
                    studentToUpdate.setPasswordAdmin(newPass);
                    break;
                case 3:
                	studentToUpdate.assignGrade();
                	break;
                case 4:
                	studentToUpdate.updateGrade();
                	break;
                case 5:
                    studentToUpdate.setCurrentSemester();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private static void manageCourses(Scanner sc) {
        while (true) {
            System.out.println("Manage Courses: " + '\n' +
                               "1. View all courses" + '\n' +
                               "2. Add a course" + '\n' +
                               "3. Remove a course" + '\n' +
                               "4. Assign TA to course" + '\n' +
                               "5. Remove TA from course" + '\n' +
                               "6. Go back" + '\n');

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
                    Course.displayAllCourses();
                    break;
                case 2:
                    addCourse(sc);
                    break;
                case 3:
                    removeCourse(sc);
                    break;
                case 4:
                	assignTAToCourse(sc);
                	break;
                case 5:
                	removeTAFromCourse(sc);
                	break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
    
    private static void addCourse(Scanner sc) {
        System.out.println("Enter course code:");
        String courseCode = sc.nextLine();

        System.out.println("Enter course name:");
        String courseName = sc.nextLine();

        System.out.println("Enter number of credits:");
        int credits = -1;
        if (sc.hasNextInt()) {
            credits = sc.nextInt();
            sc.nextLine();
        } 
        else {
            System.out.println("Invalid input for credits. Please enter a number.");
            sc.nextLine(); 
            return;
        }

        System.out.println("Enter semester the course is offered in : ");
        int availableInSem = -1;
        if (sc.hasNextInt()) {
            availableInSem = sc.nextInt();
            sc.nextLine();
        } 
        else {
            System.out.println("Invalid input for semester. Please enter a number.");
            sc.nextLine(); 
            return;
        }
        
        System.out.println("Enter the registration limit for the course : ");
        int registrationLimit = -1;
        if(sc.hasNextInt()){
        	registrationLimit = sc.nextInt();
        	if(registrationLimit <= 0){
        		System.out.println("Registration limit must be positive.");
        		return;
        	}
        	sc.nextLine();
        }
        else{
        	System.out.println("Invalid input.");
        	sc.nextLine();
        	return;
        }

        System.out.println("Enter venue : ");
        String venue = sc.nextLine();

        System.out.println("Enter timings : ");
        String timings = sc.nextLine();

        System.out.println("Enter professor username : ");
        String professorUsername = sc.nextLine();
        Professor professor = Professor.findProfessorByUsername(professorUsername);

        if (professor == null) {
            System.out.println("No professor found with username : " + professorUsername + " Returning to previous menu.");
            return;
        }

        System.out.println("Enter office hours:");
        String officeHours = sc.nextLine();

        new Course(courseCode, courseName, credits, availableInSem, registrationLimit, venue, timings, professor, officeHours);
        System.out.println("Course added successfully : " + courseCode);
    }

    private static void removeCourse(Scanner sc) {
        System.out.println("Enter course code of the course to remove : ");
        String courseCode = sc.nextLine();

        Course courseToRemove = Course.findCourseByCode(courseCode);
        if (courseToRemove == null) {
            System.out.println("No course found with code : " + courseCode);
            return;
        }

        Course.getAllCourses().remove(courseToRemove);
        System.out.println("Course removed successfully : " + courseCode);
    }
    
    private static void manageProfessors(Scanner sc) {
		while (true) {
		    int option = -1;
		    System.out.println("Choose option: " + '\n' +
		                       "1. Assign professor to course" + '\n' +
		                       "2. Go back" + '\n');
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
		            assignProfessorToCourse(sc);
		            break;
		        case 2:
		            return;  
		        default:
		            System.out.println("Invalid option.");
		            break;
		    }
		}
	}

	private static void assignProfessorToCourse(Scanner sc) {
		System.out.println("Enter course code:");
		String courseCode = sc.nextLine();
		
		Course course = Course.findCourseByCode(courseCode);
		if (course == null) {
		    System.out.println("Course with code " + courseCode + " not found.");
		    return;
		}
		Professor prevProfessor = course.getProfessor();

		System.out.println("Enter professor username:");
		String professorUsername = sc.nextLine();
		
		Professor professor = Professor.findProfessorByUsername(professorUsername);
		if (professor == null) {
		    System.out.println("No professor found with username: " + professorUsername);
		    return;
		}

		course.setProfessor(professor);
		professor.addCourse(course);
		prevProfessor.removeCourse(course);
		System.out.println("Professor " + professor.getUsername() + " assigned to course " + courseCode);
	}
	
    private static void manageComplaints(Scanner sc) {
        while (true) {
            System.out.println("Choose option: " + '\n' +
                               "1. View All Complaints" + '\n' +
                               "2. Filter Complaints" + '\n' +
                               "3. Resolve Complaint" + '\n' +
                               "4. Go back" + '\n');
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    viewComplaints(sc);
                    break;
                case 2:
                	filterComplaints(sc);
                	break;
                case 3:
                    System.out.println("Enter complaint ID to resolve:");
                    int complaintID = sc.nextInt();
                    sc.nextLine();
                    resolveComplaint(complaintID, sc);
                    break;
                case 4:
                    return; 
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
    
    public static void viewComplaints(Scanner sc) {
        Complaint.viewAllComplaints();
    }
    
    private static void assignTAToCourse(Scanner sc) {
		System.out.println("Enter course code: ");
		String courseCodeInput = sc.nextLine();

		Course courseToUpdate = Course.findCourseByCode(courseCodeInput);
		if (courseToUpdate == null) {
		    System.out.println("Course with code " + courseCodeInput + " not found.");
		    return;
		}

		System.out.println("Enter the username of TA: ");
		String usernameInput = sc.nextLine();
		TA TAToAssign = TA.findTAByUsername(usernameInput);
		Student studentToUpgrade = Student.getStudent(usernameInput);

		if (TAToAssign == null && studentToUpgrade == null) {
		    System.out.println("Student with username: " + usernameInput + " does not exist.");
		    return;
		}

		if (TAToAssign == null) {
		    System.out.println("Student with username: " + usernameInput + " found. Upgrade to TA?");
		    System.out.println("Enter: " + '\n' + "1. Yes" + '\n' + "2. No" + '\n');
		    
		    int choice = -1;
		    while (true) {
		        if (sc.hasNextInt()) {
		            choice = sc.nextInt();
		            sc.nextLine();
		            break;
		        } 
		        else {
		            System.out.println("Please enter a number.");
		            sc.nextLine(); 
		        }
		    }

		    switch (choice) {
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
		System.out.println("TA " + TAToAssign.getUsername() + " assigned to course " + courseCodeInput + " successfully.");
	}
	
	private static void removeTAFromCourse(Scanner sc) {
		System.out.println("Enter course code: ");
		String courseCodeInput = sc.nextLine();

		Course courseToUpdate = Course.findCourseByCode(courseCodeInput);
		if (courseToUpdate == null) {
		    System.out.println("Course with code " + courseCodeInput + " not found.");
		    return;
		}

		System.out.println("Enter the username of TA: ");
		String usernameInput = sc.nextLine();
		TA TAToAssign = TA.findTAByUsername(usernameInput);
		
		if (TAToAssign == null) {
			System.out.println("TA with username: " + usernameInput + "does not exist.");
			return;
		}
		
		TAToAssign.removeTAFromCourse(courseToUpdate);
	}


	public static void filterComplaints(Scanner sc) {
		int choice = -1;
		System.out.println("Choose the type of complaints you wish to view : " + '\n' +
						   "1. Pending" + '\n' +
						   "2. Resolved" + '\n' +
						   "3. Go back" + '\n');
		
		if(sc.hasNextInt()) {
			choice = sc.nextInt();
			sc.nextLine();
		}
		else {
			System.out.println("Invalid input.");
			sc.nextLine();
		}
		
		switch (choice) {
			case 1:
				Complaint.filterComplaintsByStatus(0);
				break;
			case 2:
				Complaint.filterComplaintsByStatus(1);
			case 3:
				return;
			default:
				System.out.println("Invalid option.");
				break;
		}
	}

    public static void resolveComplaint(int complaintID, Scanner sc) {
        Complaint complaintToResolve = Complaint.findComplaintByID(complaintID);
        System.out.println("Enter resolution description : ");
        String resolutionDescription = sc.nextLine();
        
        if (complaintToResolve != null && complaintToResolve.getStatus() == 0) {
            complaintToResolve.resolveComplaint(resolutionDescription);
        } 
        else {
            System.out.println("Complaint not pending or does not exist.");
        }
    }

    public void filterComplaintsByStatus(int status) {
        Complaint.filterComplaintsByStatus(status);
    }
}	
