package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lesson {
	private String name;
	private Discipline discipline;
	private Date start;
	private Date end;
	private Audience audience;
	private Teacher teacher;
	private List<Group> groups = new ArrayList<>();
	
}
