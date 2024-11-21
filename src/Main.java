package src;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Admin admin = new Admin("admin", "admin123");

        Course.initializeCourses();
        Student.initializeStudents();

        while (true) {
            System.out.println("Choose an option: " + '\n' +
                               "1. Sign Up" + '\n' +
                               "2. Login" + '\n' +
                               "3. Exit" + '\n');
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

            if (option == 1) {
                System.out.println("Choose user type: " + '\n' +
                                   "1. Student" + '\n' +
                                   "2. Professor" + '\n' +
                                   "3. Admin (Not allowed)" + '\n' +
                                   "4. Go back" + '\n');
                int userType = -1;
                if (sc.hasNextInt()) {
                    userType = sc.nextInt();
                    sc.nextLine();
                } 
                else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                    continue;
                }

                if (userType == 1) {
                    System.out.println("Enter username: ");
                    String username = sc.nextLine();
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();
                    Student.signUp(username, password, sc); 
                } 
                else if (userType == 2) {
                    System.out.println("Enter username: ");
                    String username = sc.nextLine();
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();
                    Professor.signUp(username, password, sc); 
                } 
                else if (userType == 3) {
                    System.out.println("Admin is predefined.");
                } 
                else {
                    continue;
                }
            } 
            else if (option == 2) {
                System.out.println("Choose user type: " + '\n' +
                                   "1. Student" + '\n' +
                                   "2. Professor" + '\n' +
                                   "3. Admin" + '\n' +
                                   "4. Teaching Assistant (TA)" + '\n' +
                                   "5. Go back" + '\n');
                int userType = -1;
                if (sc.hasNextInt()) {
                    userType = sc.nextInt();
                    sc.nextLine(); 
                } 
                else {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.nextLine(); 
                    continue;
                }

                if (userType == 1) {
                	try {
		                System.out.println("Enter username: ");
		                String username = sc.nextLine();
		                System.out.println("Enter password: ");
		                String password = sc.nextLine();
		                Student.loginStudent(username, password, sc);
                    }
                    catch (InvalidLoginException e) {} 
                } 
                else if (userType == 2) {
                	try {
		                System.out.println("Enter username: ");
		                String username = sc.nextLine();
		                System.out.println("Enter password: ");
		                String password = sc.nextLine();
		                Professor.loginProfessor(username, password, sc);
                    }
                    catch (InvalidLoginException e) {} 
                } 
                else if (userType == 3) {
                	try {
		                System.out.println("Enter admin username: ");
		                String adminUsername = sc.nextLine();
		                System.out.println("Enter admin password: ");
		                String adminPassword = sc.nextLine();
		                Admin.loginAdmin(adminUsername, adminPassword, sc);
                    }
                    catch (InvalidLoginException e) {}
                } 
                else if (userType == 4) {
                	try {
		            	System.out.println("Enter username: ");
		            	String username = sc.nextLine();
		            	System.out.println("Enter password: ");
		            	String password = sc.nextLine();
		            	TA.loginTA(username, password, sc);
                	}
                	catch (InvalidLoginException e) {}
                }
                else if(userType == 5){
                	break;
                }
                else {
                	System.out.println("Invalid input.");
                    continue;
                }
            } 
            else if (option == 3) {
                System.out.println("Exited successfully.");
                sc.close();
                break;
            } 
            else {
                System.out.println("Invalid option.");
            }
        }
    }
}
