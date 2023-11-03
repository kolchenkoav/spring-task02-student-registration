package com.example.studentregistration.service;

import com.example.studentregistration.config.AppProperties;
import com.example.studentregistration.event.CustomStudentEvent;
import com.example.studentregistration.init.LoadStudents;
import com.example.studentregistration.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Slf4j
@ShellComponent
public class WorkService {
    private final int minAge;
    private final int maxAge;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final boolean isEnabledListener;

    private final LoadStudents loadStudents;

    public WorkService(ApplicationEventPublisher applicationEventPublisher,
                       AppProperties appProperties,
                       LoadStudents loadStudents) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.loadStudents = loadStudents;
        isEnabledListener = appProperties.isEnabledListener();
        minAge = Integer.parseInt(appProperties.getMinAge());
        maxAge = Integer.parseInt(appProperties.getMaxAge());
        try {
            if (appProperties.isEnabledInit()) {
                initList();
            }
        } catch (Exception e) {
            log.error("==>> Error loading data from file \"{}\"", appProperties.getFileNameCsv());
        }
    }

    private final Map<Integer, Student> students = new HashMap<>();
    private Integer id = 0;

    @ShellMethod(value = "Display list of students", key = {"l", "list"})
    public String list() {
        if (students.size() > 0) {
            System.out.println("==== =====================================     ===");
            System.out.println("  ID                                  NAME     AGE");
            System.out.println("==== =====================================     ===");
            students.forEach((k, s) -> {
                System.out.printf("%4d, %s\n", k, s);
            });
        } else {
            log.info("List of students is empty");
        }
        return "";
    }

    @ShellMethod(value = "Add a new student (--fn --ln --age)", key = {"a", "add"})
    public String add(@ShellOption(value = "fn") String firstName,
                      @ShellOption(value = "ln") String lastName,
                      @ShellOption(value = "age") int age) {
        if (age < minAge || age > maxAge) {
            return "The student's age must be within the range from: " + minAge + " to: " + maxAge;
        }
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setAge(age);
        students.put(++id, student);
        applicationEventPublisher.publishEvent(new CustomStudentEvent(this, student.getFullName() + " has been added"));
        return "";
    }

    @ShellMethod(value = "Add a new student interactively", key = "ai")
    public String addIn() {
        Student student = new Student();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter student information");
        try {
            System.out.print("FirstName: ");
            student.setFirstName(br.readLine());
            System.out.print("LastName: ");
            student.setLastName(br.readLine());
            System.out.print("Age: ");
            while (true) {
                try {
                    int age = Integer.parseInt(br.readLine());
                    if (age < minAge || age > maxAge) {
                        return "The student's age must be within the range from: " + minAge + " to: " + maxAge;
                    }
                    student.setAge(age);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("--> Enter numbers only");
                    System.out.print("Age: ");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        students.put(++id, student);
        applicationEventPublisher.publishEvent(new CustomStudentEvent(this, student.getFullName() + " has been added"));
        return "";
    }

    @ShellMethod(value = "Removes a student by ID (--id)", key = {"d", "delete"})
    public String delete(@ShellOption(value = "id") int id) {
        if (students.size() == 0) {
            log.warn("List of students is empty");
            return "";
        }
        Student student = students.get(id);
        if (student == null) {
            log.warn("id: {} not found", id);
            return "";
        }
        students.remove(id);
        applicationEventPublisher.publishEvent(new CustomStudentEvent(this, student.getFullName() + "    ID: " + id + " was removed"));
        return "";
    }

    @ShellMethod(value = "Clear the list", key = "c")
    public String clearList() {
        int count = students.size();
        students.clear();
        log.info("{} students were removed", count);
        return "";
    }

    @EventListener(ApplicationReadyEvent.class)
    public String initList() {
        int count;
        List<Student> lines = loadStudents.load();
        count = lines.size();
        lines.forEach(s -> students.put(++id, s));

        if (isEnabledListener) {
            applicationEventPublisher.publishEvent(new CustomStudentEvent(this, loadStudents.nameImpl() + count + " students <<=="));
        }
        return "";
    }
}
