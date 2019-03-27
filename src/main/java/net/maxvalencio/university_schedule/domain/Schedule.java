package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private Long id;
    private List<Lesson> lessons = new ArrayList<>();

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
    }
}
