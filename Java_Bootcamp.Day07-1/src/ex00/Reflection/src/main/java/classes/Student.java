package classes;

import java.util.Objects;

public class Student {

	private String firstName;
	private String midName;
	private String lastName;
	private String rate;
	
	public Student() {}

	public Student(String firstName, String midName, String lastName, String rate) {
		this.firstName = firstName;
		this.midName = midName;
		this.lastName = lastName;
		this.rate = rate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return Objects.equals(firstName, student.firstName) && Objects.equals(midName, student.midName) && Objects.equals(lastName, student.lastName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, midName, lastName);
	}

	@Override
	public String toString() {
		return "Student{" +
				"firstName='" + firstName + '\'' +
				", midName='" + midName + '\'' +
				", lastName='" + lastName + '\'' +
				", rate='" + rate + '\'' +
				'}';
	}
}