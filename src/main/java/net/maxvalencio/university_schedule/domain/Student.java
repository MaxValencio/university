package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;

public class Student extends Person implements Serializable {

	private String course;

	public Student() {
		super();
	}

	public Student(String course) {
		super();
		this.course = course;
	}

	public Student(String name, String emailAddress, String course) {
		super(name, emailAddress);
		this.course = course;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Student other = (Student) obj;
		if (course == null) {
			if (other.course != null) {
				return false;
			}
		} else if (!course.equals(other.course)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Student : " + getName() + ",\n" 
				+ "	email = " + getEmailAddress() + ",\n"
				+ "	course = " + course + ".";
	}
}
