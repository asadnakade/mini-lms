package com.example.minilms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Entity representing a Lesson within a Module
 */
@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Lesson title is required")
    @Size(max = 255, message = "Lesson title must not exceed 255 characters")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Lesson type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LessonType type;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "order_index")
    private Integer orderIndex;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonBackReference("module-lessons")
    private Module module;

    // JPA lifecycle methods
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Lesson() {}

    public Lesson(String title, LessonType type) {
        this.title = title;
        this.type = type;
    }

    public Lesson(String title, LessonType type, String content) {
        this.title = title;
        this.type = type;
        this.content = content;
    }

    // Content validation based on lesson type
    public boolean isContentValid() {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        return switch (type) {
            case TEXT -> content.length() <= 10000; // Max 10k characters for text
            case VIDEO -> isValidUrl(content) && (content.contains("youtube.com") ||
                    content.contains("vimeo.com") ||
                    content.endsWith(".mp4"));
            case IMAGE -> isValidUrl(content) && (content.endsWith(".jpg") ||
                    content.endsWith(".jpeg") ||
                    content.endsWith(".png") ||
                    content.endsWith(".gif"));
            case PDF -> isValidUrl(content) && content.endsWith(".pdf");
        };
    }

    private boolean isValidUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", orderIndex=" + orderIndex +
                ", createdAt=" + createdAt +
                '}';
    }
}