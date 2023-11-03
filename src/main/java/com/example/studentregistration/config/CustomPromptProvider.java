package com.example.studentregistration.config;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomPromptProvider implements PromptProvider
{
    @Override
    public org.jline.utils.AttributedString getPrompt()
    {
        return new AttributedString(
                "WorkService cool shell " + "==> ",
                AttributedStyle.DEFAULT.background(AttributedStyle.GREEN));
    }
}