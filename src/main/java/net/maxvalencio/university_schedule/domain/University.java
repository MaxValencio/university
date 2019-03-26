package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class University implements Serializable {

	private String name;
	private List<Faculty> faculties = new ArrayList<>();
	private List<Audience> audiences = new ArrayList<>();
	private Schedule schedule = new Schedule();

	public University(String name) {
		this.setName(name);
	}

	public void addFaculty(Faculty faculty) {
		faculties.add(faculty);
	}

	public List<Faculty> getFaculties() {
		return faculties;
	}

	public void addAudience(Audience audience) {
		audiences.add(audience);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Schedule getSchedule() {
		return schedule;
	}
}
