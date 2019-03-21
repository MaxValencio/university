package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person{

	private String qualification;
	private List<Discipline> disciplines = new ArrayList<>();
	
	public Teacher(String name, String emailAddress, String qualification, List<Discipline> disciplines) {
		super(name, emailAddress);
		this.qualification = qualification;
		this.disciplines = disciplines;
	}
}
