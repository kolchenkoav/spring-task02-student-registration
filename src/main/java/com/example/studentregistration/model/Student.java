package com.example.studentregistration.model;

import lombok.Data;

@Data
public class Student {
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public String toString() {
        return String.format(" %35s, %6d", firstName + " " + lastName, age);
    }

    public String getFullName() {
        return String.format(" %35s", firstName + " " + lastName);
    }
}
