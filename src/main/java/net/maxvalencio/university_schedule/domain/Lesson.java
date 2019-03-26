package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lesson implements Serializable {
	private String name;
	private Discipline discipline;
	private Date start;
	private Date end;
	private Audience audience;
	private Teacher teacher;
	private List<Group> groups = new ArrayList<>();

	public Lesson() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
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
		result = prime * result + ((audience == null) ? 0 : audience.hashCode());
		result = prime * result + ((discipline == null) ? 0 : discipline.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((groups == null) ? 0 : groups.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
		if (audience == null) {
			if (other.audience != null) {
				return false;
			}
		} else if (!audience.equals(other.audience)) {
			return false;
		}
		if (discipline == null) {
			if (other.discipline != null) {
				return false;
			}
		} else if (!discipline.equals(other.discipline)) {
			return false;
		}
		if (end == null) {
			if (other.end != null) {
				return false;
			}
		} else if (!end.equals(other.end)) {
			return false;
		}
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
		if (start == null) {
			if (other.start != null) {
				return false;
			}
		} else if (!start.equals(other.start)) {
			return false;
		}
		if (teacher == null) {
			if (other.teacher != null) {
				return false;
			}
		} else if (!teacher.equals(other.teacher)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Lesson \n" + " name = " + name + ",\n"
				+ " discipline = " + discipline + ",\n" 
				+ " start = " + start + ",\n"
				+ " end = " + end + ",\n" 
				+ " audience = " + audience + ",\n"
				+ " teacher = " + teacher + ",\n" 
				+ " groups=" + groups + ".";
	}
}
