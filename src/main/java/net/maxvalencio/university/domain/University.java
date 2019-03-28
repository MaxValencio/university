package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;

public class University {

    private Long id;
    private String name;
    private List<Faculty> faculties = new ArrayList<>();
    private List<Audience> audiences = new ArrayList<>();
    private Schedule schedule = new Schedule();

    public University(String name) {
        this.setName(name);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Faculty> getFaculties() {
        return faculties;
    }

    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    public void addAudience(Audience audience) {
        audiences.add(audience);
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
