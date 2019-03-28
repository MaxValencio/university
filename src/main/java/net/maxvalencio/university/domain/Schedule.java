package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<Lesson> lessons = new ArrayList<>();

    public Schedule() {
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
