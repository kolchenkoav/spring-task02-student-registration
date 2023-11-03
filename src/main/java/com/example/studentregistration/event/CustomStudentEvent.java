package com.example.studentregistration.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CustomStudentEvent extends ApplicationEvent {
    private String source;
    private final String message;

    public CustomStudentEvent(Object source, String message) {
        super(source);
        this.message = message;
        this.source = source.getClass().getSimpleName();
    }
}
