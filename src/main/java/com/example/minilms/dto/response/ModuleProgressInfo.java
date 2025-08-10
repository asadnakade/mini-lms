package com.example.minilms.dto.response;

/**
 * DTO for module progress information
 */
public class ModuleProgressInfo {

    private Long moduleId;
    private String moduleTitle;
    private double progressPercentage;
    private int totalLessons;
    private int completedLessons;
    private int startedLessons;

    // Constructors
    public ModuleProgressInfo() {}

    public ModuleProgressInfo(Long moduleId, String moduleTitle) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
    }

    // Getters and Setters
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public String getModuleTitle() { return moduleTitle; }
    public void setModuleTitle(String moduleTitle) { this.moduleTitle = moduleTitle; }

    public double getProgressPercentage() { return progressPercentage; }
    public void setProgressPercentage(double progressPercentage) { this.progressPercentage = progressPercentage; }

    public int getTotalLessons() { return totalLessons; }
    public void setTotalLessons(int totalLessons) { this.totalLessons = totalLessons; }

    public int getCompletedLessons() { return completedLessons; }
    public void setCompletedLessons(int completedLessons) { this.completedLessons = completedLessons; }

    public int getStartedLessons() { return startedLessons; }
    public void setStartedLessons(int startedLessons) { this.startedLessons = startedLessons; }
}