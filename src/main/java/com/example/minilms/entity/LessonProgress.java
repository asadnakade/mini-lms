package com.example.minilms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Entity representing a User's progress on a specific Lesson
 */
@Entity
@Table(name = "lesson_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "lesson_id"}))
public class LessonProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotNull(message = "Lesson ID is required")
    @Column(name = "lesson_id", nullable = false)
    private Long lessonId;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "completion_percentage")
    private Integer completionPercentage = 0;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // JPA lifecycle methods
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;

        if (startedAt == null) {
            startedAt = now;
        }

        if (completed && completedAt == null) {
            completedAt = now;
            completionPercentage = 100;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();

        if (completed && completedAt == null) {
            completedAt = LocalDateTime.now();
            completionPercentage = 100;
        } else if (!completed) {
            completedAt = null;
            if (completionPercentage == 100) {
                completionPercentage = 0;
            }
        }
    }

    // Constructors
    public LessonProgress() {}

    public LessonProgress(String userId, Long lessonId) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.completed = false;
        this.completionPercentage = 0;
    }

    public LessonProgress(String userId, Long lessonId, Boolean completed) {
        this.userId = userId;
        this.lessonId = lessonId;
        this.completed = completed;
        this.completionPercentage = completed ? 100 : 0;
    }

    // Helper methods
    public void markAsCompleted() {
        this.completed = true;
        this.completionPercentage = 100;
        this.completedAt = LocalDateTime.now();
    }

    public void markAsIncomplete() {
        this.completed = false;
        this.completionPercentage = 0;
        this.completedAt = null;
    }

    public void updateProgress(int percentage) {
        this.completionPercentage = Math.max(0, Math.min(100, percentage));
        if (this.completionPercentage == 100) {
            markAsCompleted();
        } else {
            this.completed = false;
            this.completedAt = null;
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(Integer completionPercentage) {
        this.completionPercentage = completionPercentage;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "LessonProgress{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", lessonId=" + lessonId +
                ", completed=" + completed +
                ", completionPercentage=" + completionPercentage +
                ", completedAt=" + completedAt +
                '}';
    }
}