package net.maxvalencio.university_schedule.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private String qualification;
    private List<Discipline> disciplines = new ArrayList<>();

    public Teacher() {
        super();
    }

    public Teacher(String qualification, List<Discipline> disciplines) {
        super();
        this.qualification = qualification;
        this.disciplines = disciplines;
    }

    public Teacher(String name, String emailAddress, String qualification,
            List<Discipline> disciplines) {
        super(name, emailAddress);
        this.qualification = qualification;
        this.disciplines = disciplines;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public List<Discipline> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<Discipline> disciplines) {
        this.disciplines = disciplines;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((disciplines == null) ? 0 : disciplines.hashCode());
        result = prime * result
                + ((qualification == null) ? 0 : qualification.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) obj;
        if (disciplines == null) {
            if (other.disciplines != null) {
                return false;
            }
        } else if (!disciplines.equals(other.disciplines)) {
            return false;
        }
        if (qualification == null) {
            if (other.qualification != null) {
                return false;
            }
        } else if (!qualification.equals(other.qualification)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", name()=" + getName() + "]";
    }
}
