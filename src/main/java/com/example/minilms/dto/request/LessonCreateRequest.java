package com.example.minilms.dto.request;

import com.example.minilms.entity.LessonType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new lesson
 */
public class LessonCreateRequest {

    @NotBlank(message = "Lesson title is required")
    @Size(max = 255, message = "Lesson title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Lesson type is required")
    private LessonType type;

    private String content;

    private Integer orderIndex;

    // Constructors
    public LessonCreateRequest() {}

    public LessonCreateRequest(String title, LessonType type, String content) {
        this.title = title;
        this.type = type;
        this.content = content;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LessonType getType() { return type; }
    public void setType(LessonType type) { this.type = type; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
}