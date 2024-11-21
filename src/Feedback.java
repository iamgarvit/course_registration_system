package src;

import java.util.Scanner;
import java.util.ArrayList;

public class Feedback<R, C> {
	private R rating;
	private C comment;
	private Student student;
	private Course course;
	private static ArrayList<Feedback> allFeedback = new ArrayList<>();
	
	public Feedback(R rating, C comment, Student student, Course course) {
		this.rating = rating;
		this.comment = comment;
		this.student = student;
		this.course = course;
		allFeedback.add(this);
		course.addFeedback(this);
	}
	
	public R getRating() {
		return rating;
	}
	
	public C getComment() {
		return comment;
	}
	
	public Student getStudentSubmit() {
		return student;
	}
	
	public Course getCourse() {
		return course;
	}
} 
