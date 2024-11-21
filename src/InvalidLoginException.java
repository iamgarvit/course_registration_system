package src;

public class InvalidLoginException extends Exception {
	public InvalidLoginException() {
		System.out.println("Login failed. Invalid credentials.");
	}
}
