package com.example.login.securityForm.student;

import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"MgMyo"),
            new Student(2,"KoKo"),
            new Student(3,"TheRock")

    );


@GetMapping(path = "{studentId}")
    public Student getStudent(@PathVariable("studentId")Integer studentId){

    return STUDENTS.stream()
            .filter(student -> studentId.equals(student.getStudentId()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("Student" + studentId + "does not exit"));
    }
}
