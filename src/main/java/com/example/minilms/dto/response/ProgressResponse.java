package com.example.minilms.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for progress information response
 */
public class ProgressResponse {

    private String userId;
    private Long entityId; // courseId or moduleId
    private String entityType; // "course" or "module"
    private String entityTitle;
    private double progressPercentage;
    private int totalLessons;
    private int completedLessons;
    private int startedLessons;
    private List<ModuleProgressInfo> moduleProgresses; // For course progress
    private List<LessonProgressInfo> lessonProgresses; // For module progress
    private LocalDateTime lastUpdated;

    // Constructors
    public ProgressResponse() {}

    public ProgressResponse(String userId, Long entityId, String entityType, String entityTitle) {
        this.userId = userId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.entityTitle = entityTitle;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public String getEntityTitle() { return entityTitle; }
    public void setEntityTitle(String entityTitle) { this.entityTitle = entityTitle; }

    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }

    public int getTotalLessons() { return totalLessons; }
    public void setTotalLessons(int totalLessons) { this.totalLessons = totalLessons; }

    public int getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(int completedLessons) { this.completedLessons = completedLessons; }

    public int getStartedLessons() { return startedLessons; }
    public void setStartedLessons(int startedLessons) { this.startedLessons = startedLessons; }

    public List<ModuleProgressInfo> getModuleProgresses() { return moduleProgresses; }
    public void setModuleProgresses(List<ModuleProgressInfo> moduleProgresses) { this.moduleProgresses = moduleProgresses; }

    public List<LessonProgressInfo> getLessonProgresses() { return lessonProgresses; }
    public void setLessonProgresses(List<LessonProgressInfo> lessonProgresses) { this.lessonProgresses = lessonProgresses; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
}