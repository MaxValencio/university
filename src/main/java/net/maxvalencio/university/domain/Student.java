package net.maxvalencio.university.domain;

public class Student extends Person {

    private String course;

    public Student(Long id, String name, String emailAddress) {
        super(id, name, emailAddress);
    }

    public Student(Long id, String name, String emailAddress, String course) {
        super(id, name, emailAddress);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((course == null) ? 0 : course.hashCode());
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
        Student other = (Student) obj;
        if (course == null) {
            if (other.course != null) {
                return false;
            }
        } else if (!course.equals(other.course)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", name=" + getName() + "]";
    }
}
