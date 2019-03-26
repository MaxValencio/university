package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Faculty implements Serializable {

	private String name;
	private List<Teacher> teachers = new ArrayList<>();
	private List<Student> students = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();

	public Faculty() {
	}

	public Faculty(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void removeTeacher(Teacher teacher) {
		teachers.remove(teacher);
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void removeStudent(Student student) {
		students.remove(student);
	}

	public List<Teacher> getTechears() {
		return teachers;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
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
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
		result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
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
		Faculty other = (Faculty) obj;
		if (groups == null) {
			if (other.groups != null) {
				return false;
			}
		} else if (!groups.equals(other.groups)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (students == null) {
			if (other.students != null) {
				return false;
			}
		} else if (!students.equals(other.students)) {
			return false;
		}
		if (teachers == null) {
			if (other.teachers != null) {
				return false;
			}
		} else if (!teachers.equals(other.teachers)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Faculty : \n" 
				+ "	name = " + name + ",\n" 
				+ "	teachers = " + teachers + ",\n"
				+ "	students = " + students + ",\n"
				+ "	groups = " + groups + ".";
	}
	
	

}
