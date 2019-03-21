package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class University {

	private String name;
	private List<Faculty> faculties = new ArrayList<>();
	private List<Audience> audiences = new ArrayList<>();
	private Schedule schedule = new Schedule();
	
	public University(String name) {
		this.name = name;
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
	
	public Schedule showScheduleDay(Date date, Person person) {
		return new Schedule();
	}
	
	public Schedule showSchedule(Date startDate, Date endDate, Person person) {
		return new Schedule();
	}
}
