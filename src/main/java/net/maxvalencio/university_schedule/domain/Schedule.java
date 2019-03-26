package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {

	private List<Lesson> lessons = new ArrayList<>();

	public Schedule() {
	}

	public void addLesson(Lesson lesson) {
		lessons.add(lesson);
	}

	public void removeLesson(Lesson lesson) {
		lessons.remove(lesson);
	}

	public List<Lesson> getLessons() {
		return lessons;
	}
}
