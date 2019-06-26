package net.maxvalencio.university.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private Long id;
    private String name;
    private String emailAddress;
    private String qualification;
    private List<Discipline> disciplines = new ArrayList<>();

    public Teacher() {}
    
    public Teacher(String name) {
        this.setName(name);;
    }

    public Teacher(String name, String qualification) {
        this.setName(name);
        this.qualification = qualification;
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

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
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
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((qualification == null) ? 0 : qualification.hashCode());
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
        Teacher other = (Teacher) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
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
        return "Teacher [id=" + id + ", name=" + name + ", qualification=" + qualification+ "]";
    }
}
