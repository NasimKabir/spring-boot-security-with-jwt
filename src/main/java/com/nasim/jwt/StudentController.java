package com.nasim.jwt;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
	public static final List<Student> students = Arrays.asList(
			new Student(1, "Nasim","nasim"),
			new Student(2, "Nayeem","nayeem"),
			new Student(3, "Jannat","jannat"), 
			new Student(4, "Shahadat","shahadat"));

	@GetMapping("/{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		return students.stream().filter((student) -> studentId.equals(student.getStudentId())).findFirst()
				.orElseThrow(() -> new IllegalStateException("Student " + studentId + "does not exits"));
	}

	@GetMapping("admin/hello")
	public String hellowordAdmin() {
		return "Hello admin panel";
	}
}