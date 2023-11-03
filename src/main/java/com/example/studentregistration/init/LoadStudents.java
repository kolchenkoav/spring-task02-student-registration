package com.example.studentregistration.init;

import com.example.studentregistration.model.Student;

import java.util.List;

public interface LoadStudents {
    String nameImpl();
    List<Student> load();

}
