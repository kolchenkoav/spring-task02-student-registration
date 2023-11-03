package com.example.studentregistration.init;

import com.example.studentregistration.model.Student;
import com.example.studentregistration.config.AppProperties;
import com.example.studentregistration.generaror.GeneratorStudentImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnProperty(name = "application.init.type", havingValue = "gen")
public class LoadStudentsFromGenImpl implements LoadStudents {
    private final AppProperties appProperties;
    private final GeneratorStudentImpl generator;

    public LoadStudentsFromGenImpl(AppProperties appProperties, GeneratorStudentImpl generator) {
        this.appProperties = appProperties;
        this.generator = generator;
    }

    @Override
    public String nameImpl() {
        return "==>> Loaded students from the generator: ";
    }

    @Override
    public List<Student> load() {
        List<Student> lines = new ArrayList<>();
        for (int i = 0; i < appProperties.getNumberOfRecords(); i++) {
            lines.add(generator.getGeneratedEntity());
        }
        return lines;
    }
}
