package com.example.studentregistration.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomStudentEventListener implements ApplicationListener<CustomStudentEvent> {
    @Override
    public void onApplicationEvent(CustomStudentEvent event) {
        log.info(event.getSource() + ": " +
                event.getMessage());
    }
}
