package src;

public class CourseFullException extends Exception {
	public CourseFullException() {
		System.out.println("The registration limit is reached.");
	}
}
