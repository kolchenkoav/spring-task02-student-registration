package com.example.studentregistration.init;

import com.example.studentregistration.model.Student;
import com.example.studentregistration.config.AppProperties;
import com.example.studentregistration.parse.ParseLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Component
@ConditionalOnProperty(name = "application.init.type", havingValue = "csv")
public class LoadStudentsFromCSVImpl implements LoadStudents {
    private final ParseLine parseLine;
    private final String fileName;

    public LoadStudentsFromCSVImpl(ParseLine parseLine, AppProperties appProperties) {
        this.parseLine = parseLine;
        fileName = appProperties.getFileNameCsv();
    }

    @Override
    public String nameImpl() {
        return "==>> Loaded students from the file \"" + fileName + "\": ";
    }

    @Override
    public List<Student> load() {
        try {
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                    .getResource(fileName)).toURI());

            try (Stream<String> lines = Files.lines(path)) {
                List<Student> list = new ArrayList<>();
                for (String line : lines.toList()) {
                    list.add(parseLine.parseLine(line));
                }
                return list;
            } catch (Exception e) {
                log.error("File \"{}\": parsing error", fileName);
            }
        } catch (Exception e) {
            log.error("URISyntaxException: \"{}\": not found", fileName);
        }
        return null;
    }
}
