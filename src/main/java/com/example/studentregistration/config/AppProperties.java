package com.example.studentregistration.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.example")
@Configuration
@PropertySource("classpath:application.yaml")
@Getter
public class AppProperties {
    public AppProperties() {
        System.setProperty("org.jline.terminal.dumb", "true");
    }

    @Value("${application.filename-csv}")
    private String fileNameCsv;

    @Value("${application.number-of-records}")
    private int numberOfRecords;

    @Value("${application.init.enabled}")
    private boolean isEnabledInit;

    @Value("${application.init.type}")
    private String type;

    @Value("${application.delimiter}")
    private String delimiter;

    @Value("${application.min-age}")
    private String minAge;

    @Value("${application.max-age}")
    private String maxAge;

    @Value("${application.init.listener}")
    private boolean isEnabledListener;
}
