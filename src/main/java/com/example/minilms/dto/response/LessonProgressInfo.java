package com.example.minilms.dto.response;

import com.example.minilms.entity.LessonType;
import java.time.LocalDateTime;

/**
 * DTO for lesson progress information
 */
public class LessonProgressInfo {

    private Long lessonId;
    private String lessonTitle;
    private LessonType lessonType;
    private boolean completed;
    private int completionPercentage;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    // Constructors
    public LessonProgressInfo() {}

    public LessonProgressInfo(Long lessonId, String lessonTitle, LessonType lessonType) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonType = lessonType;
    }

    // Getters and Setters
    public Long getLessonId() { return lessonId; }
    public void setLessonId(Long lessonId) { this.lessonId = lessonId; }

    public String getLessonTitle() { return lessonTitle; }
    public void setLessonTitle(String lessonTitle) { this.lessonTitle = lessonTitle; }

    public LessonType getLessonType() { return lessonType; }
    public void setLessonType(LessonType lessonType) { this.lessonType = lessonType; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public int getCompletionPercentage() { return completionPercentage; }
    public void setCompletionPercentage(int completionPercentage) { this.completionPercentage = completionPercentage; }

    public LocalDateTime getStartedAt() { return startedAt; }
    public void setStartedAt(LocalDateTime startedAt) { this.startedAt = startedAt; }

    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
}