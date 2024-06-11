package com.corbinelli.lorenzo.swimming.model;

import java.util.Objects;

public class Swimmer {

	private String id;
	private String name;
	private String gender;
	private String mainStroke;

	public Swimmer(String id, String name, String gender, String mainStroke) {
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.mainStroke = mainStroke;
	}

	public Swimmer() {
		
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public String getMainStroke() {
		return mainStroke;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gender, id, mainStroke, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Swimmer other = (Swimmer) obj;
		return Objects.equals(gender, other.gender) && Objects.equals(id, other.id)
				&& Objects.equals(mainStroke, other.mainStroke) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Swimmer [id=" + id + ", name=" + name + ", gender=" + gender + ", mainStroke=" + mainStroke + "]";
	}
	
}
