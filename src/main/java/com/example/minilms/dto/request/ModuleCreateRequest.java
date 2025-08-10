package com.example.minilms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for creating a new module
 */
public class ModuleCreateRequest {

    @NotBlank(message = "Module title is required")
    @Size(max = 255, message = "Module title must not exceed 255 characters")
    private String title;

    @Size(max = 1000, message = "Module summary must not exceed 1000 characters")
    private String summary;

    @Size(max = 500, message = "Thumbnail URL must not exceed 500 characters")
    private String thumbnailUrl;

    @Size(max = 500, message = "Cover image URL must not exceed 500 characters")
    private String coverImageUrl;

    // Constructors
    public ModuleCreateRequest() {}

    public ModuleCreateRequest(String title, String summary) {
        this.title = title;
        this.summary = summary;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
}