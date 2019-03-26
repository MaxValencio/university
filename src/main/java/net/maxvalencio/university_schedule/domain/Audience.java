package net.maxvalencio.university_schedule.domain;

import java.io.Serializable;

public class Audience implements Serializable {

	private int number;

	public Audience() {
	}

	public Audience(int number) {
		this.setNumber(number);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
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
		Audience other = (Audience) obj;
		if (number != other.number) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Audience № " + number;
	}
}
