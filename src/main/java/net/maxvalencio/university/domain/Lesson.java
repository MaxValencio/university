package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lesson {

    private Long id;
    private Discipline discipline;
    private String dateStart;
    private String dateEnd;
    private Audience audience;
    private Teacher teacher;
    private List<Group> groups = new ArrayList<>();

    public Lesson() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((discipline == null) ? 0 : discipline.hashCode());
        result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
        result = prime * result + ((groups == null) ? 0 : groups.hashCode());
        result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
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
        Lesson other = (Lesson) obj;
        if (discipline == null) {
            if (other.discipline != null) {
                return false;
            }
        } else if (!discipline.equals(other.discipline)) {
            return false;
        }
        if (dateEnd == null) {
            if (other.dateEnd != null) {
                return false;
            }
        } else if (dateEnd.equals(other.dateEnd)) {
            return false;
        }
        if (groups == null) {
            if (other.groups != null) {
                return false;
            }
        } else if (!groups.equals(other.groups)) {
            return false;
        }
        if (dateStart == null) {
            if (other.dateStart != null) {
                return false;
            }
        } else if (!dateStart.equals(other.dateStart)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lesson [id=" + id + ", discipline="
                + discipline + ", audience=" + audience + "]";
    }
}
