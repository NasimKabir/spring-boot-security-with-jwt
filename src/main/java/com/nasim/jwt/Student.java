package com.nasim.jwt;

public class Student {
	private final Integer studentId;
	private final String studentName;
private final String password;
	
	
	
	public Integer getStudentId() {
		return studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getPassword() {
		return password;
	}

	public Student(Integer studentId, String studentName, String password) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.password = password;
	}

	

}
