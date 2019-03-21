package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Faculty {

	private String name;
	private List<Teacher> teachers = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	private List<Student> students = new ArrayList<>();
}
