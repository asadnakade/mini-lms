package com.example.minilms.entity;

/**
 * Enum representing different types of lessons supported by the LMS
 */
public enum LessonType {
    TEXT("Text-based lesson"),
    VIDEO("Video lesson"),
    IMAGE("Image-based lesson"),
    PDF("PDF document lesson");

    private final String description;

    LessonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}