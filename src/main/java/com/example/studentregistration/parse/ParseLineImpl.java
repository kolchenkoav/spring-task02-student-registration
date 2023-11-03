package com.example.studentregistration.parse;

import com.example.studentregistration.model.Student;
import com.example.studentregistration.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ParseLineImpl implements ParseLine {
    private final static int numberOfWordsPerLine = 3;      // "Bill, Gates, 50": line contains 3 words;  Delimiter: ","
    private final AppProperties appProperties;

    public ParseLineImpl(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public Student parseLine(String line) {
        String delimiter = appProperties.getDelimiter();
        Student student = new Student();
        String[] values = line.split(delimiter);
        if (values.length != numberOfWordsPerLine) {
            log.error("Line: \"{}\"   Delimiter: \"{}\"", line, appProperties.getDelimiter());
        }
        student.setFirstName(values[0].trim());
        student.setLastName(values[1].trim());
        try {
            student.setAge(Integer.parseInt(values[2].trim()));
        } catch (NumberFormatException e) {
            log.error("Line: \"{}\" Error getting age from: \"{}\"", line, values[2].trim());
        }
        return student;
    }
}
