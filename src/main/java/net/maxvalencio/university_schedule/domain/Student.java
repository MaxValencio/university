package net.maxvalencio.university_schedule.domain;

public class Student extends Person{

	private String course;
	
	public Student(String name, String emailAddress, String course) {
		super(name, emailAddress);
		this.course = course;
	}
}
