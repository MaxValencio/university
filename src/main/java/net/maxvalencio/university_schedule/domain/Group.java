package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group {

    private Long id = 0L;
    private String name = "N/A";
    private List<Student> students = new ArrayList<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result * name.hashCode() * students.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Group other = (Group) obj;
        if (!id.equals(other.id)) {
            return false;
        }
        if (!name.equals(other.name)) {
            return false;
        }
        if (!students.equals(other.students)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Group: " + name + ",\n" + " students = " + students + ".";
    }
}
