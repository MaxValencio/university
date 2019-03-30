package net.maxvalencio.university.domain;

public class Student extends Person {

    private Long studentCardNumber;
    private String course;

    public Student(String name) {
        this.setName(name);
    }

    public Student(String name, String emailAddress, String course) {
        this.setName(name);
        this.setEmailAddress(emailAddress);
        this.course = course;
    }

    public Long getStudentCardNumber() {
        return studentCardNumber;
    }

    public void setStudentCardNumber(Long studentCardNumber) {
        this.studentCardNumber = studentCardNumber;
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
        result = prime * result + ((studentCardNumber == null)
                ? 0
                : studentCardNumber.hashCode());
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
        if (studentCardNumber == null) {
            if (other.studentCardNumber != null) {
                return false;
            }
        } else if (!studentCardNumber.equals(other.studentCardNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Student [id=" + getId() + ", name=" + getName()
                + "studentCardNumber=" + studentCardNumber + "]";
    }
}
