package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {

    private Long taxpayerID;
    private String qualification;
    private List<Discipline> disciplines = new ArrayList<>();

    public Teacher(String name) {
        this.setName(name);;
    }

    public Teacher(String name, String qualification) {
        this.setName(name);
        this.qualification = qualification;
    }

    public Long getTaxpayerID() {
        return taxpayerID;
    }

    public void setTaxpayerID(Long taxpayerID) {
        this.taxpayerID = taxpayerID;
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
                + ((taxpayerID == null) ? 0 : taxpayerID.hashCode());
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        Teacher other = (Teacher) obj;
        if (taxpayerID == null) {
            if (other.taxpayerID != null) {
                return false;
            }
        } else if (!taxpayerID.equals(other.taxpayerID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + getId() + ", name=" + getName() + "taxpayerID="
                + taxpayerID + "]";
    }
}
