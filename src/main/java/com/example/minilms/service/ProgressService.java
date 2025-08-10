package com.example.minilms.service;

import com.example.minilms.dto.response.LessonProgressInfo;
import com.example.minilms.dto.response.ModuleProgressInfo;
import com.example.minilms.dto.response.ProgressResponse;
import com.example.minilms.entity.*;
import com.example.minilms.entity.Module;
import com.example.minilms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for handling progress-related operations
 */
@Service
@Transactional
public class ProgressService {

    private final LessonProgressRepository lessonProgressRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Autowired
    public ProgressService(LessonProgressRepository lessonProgressRepository,
                           CourseRepository courseRepository,
                           ModuleRepository moduleRepository,
                           LessonRepository lessonRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    /**
     * Mark lesson as completed or update progress
     */
    public LessonProgress updateLessonProgress(String userId, Long lessonId, Boolean completed, Integer completionPercentage) {
        // Validate lesson exists
        if (!lessonRepository.existsById(lessonId)) {
            throw new RuntimeException("Lesson not found with id: " + lessonId);
        }

        Optional<LessonProgress> existingProgress = lessonProgressRepository.findByUserIdAndLessonId(userId, lessonId);

        LessonProgress progress;
        if (existingProgress.isPresent()) {
            progress = existingProgress.get();
        } else {
            progress = new LessonProgress(userId, lessonId);
        }

        // Update progress based on parameters
        if (completed != null) {
            if (completed) {
                progress.markAsCompleted();
            } else {
                progress.markAsIncomplete();
            }
        } else if (completionPercentage != null) {
            progress.updateProgress(completionPercentage);
        }

        return lessonProgressRepository.save(progress);
    }

    /**
     * Get course-level progress for a user
     */
    @Transactional(readOnly = true)
    public ProgressResponse getCourseProgress(String userId, Long courseId) {
        Course course = courseRepository.findByIdWithModulesAndLessons(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        ProgressResponse response = new ProgressResponse(userId, courseId, "course", course.getTitle());

        // Get all lesson IDs in the course
        List<Long> allLessonIds = course.getModules().stream()
                .flatMap(module -> module.getLessons().stream())
                .map(Lesson::getId)
                .collect(Collectors.toList());

        if (allLessonIds.isEmpty()) {
            response.setProgressPercentage(0.0);
            response.setTotalLessons(0);
            response.setCompletedLessons(0);
            response.setStartedLessons(0);
            return response;
        }

        // Get progress for all lessons in the course
        List<LessonProgress> userProgress = lessonProgressRepository.findByUserIdAndCourseId(userId, courseId);
        Map<Long, LessonProgress> progressMap = userProgress.stream()
                .collect(Collectors.toMap(LessonProgress::getLessonId, p -> p));

        // Calculate module progresses
        List<ModuleProgressInfo> moduleProgresses = new ArrayList<>();
        double totalModuleProgress = 0.0;
        int modulesWithLessons = 0;

        for (Module module : course.getModules()) {
            if (!module.getLessons().isEmpty()) {
                ModuleProgressInfo moduleProgress = calculateModuleProgress(userId, module, progressMap);
                moduleProgresses.add(moduleProgress);
                totalModuleProgress += moduleProgress.getProgressPercentage();
                modulesWithLessons++;
            }
        }

        // Set course-level statistics
        response.setTotalLessons(allLessonIds.size());
        response.setCompletedLessons((int) userProgress.stream().filter(LessonProgress::getCompleted).count());
        response.setStartedLessons(userProgress.size());
        response.setProgressPercentage(modulesWithLessons > 0 ? totalModuleProgress / modulesWithLessons : 0.0);
        response.setModuleProgresses(moduleProgresses);

        // Set last updated timestamp
        response.setLastUpdated(userProgress.stream()
                .map(LessonProgress::getUpdatedAt)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now()));

        return response;
    }

    /**
     * Get module-level progress for a user
     */
    @Transactional(readOnly = true)
    public ProgressResponse getModuleProgress(String userId, Long moduleId) {
        Module module = moduleRepository.findByIdWithLessons(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId));

        ProgressResponse response = new ProgressResponse(userId, moduleId, "module", module.getTitle());

        if (module.getLessons().isEmpty()) {
            response.setProgressPercentage(0.0);
            response.setTotalLessons(0);
            response.setCompletedLessons(0);
            response.setStartedLessons(0);
            return response;
        }

        // Get progress for all lessons in the module
        List<LessonProgress> userProgress = lessonProgressRepository.findByUserIdAndModuleId(userId, moduleId);
        Map<Long, LessonProgress> progressMap = userProgress.stream()
                .collect(Collectors.toMap(LessonProgress::getLessonId, p -> p));

        // Calculate lesson progresses
        List<LessonProgressInfo> lessonProgresses = module.getLessons().stream()
                .map(lesson -> createLessonProgressInfo(lesson, progressMap.get(lesson.getId())))
                .collect(Collectors.toList());

        // Calculate module statistics
        int totalLessons = module.getLessons().size();
        int completedLessons = (int) userProgress.stream().filter(LessonProgress::getCompleted).count();
        int startedLessons = userProgress.size();
        double progressPercentage = totalLessons > 0 ? (double) completedLessons / totalLessons * 100 : 0.0;

        response.setTotalLessons(totalLessons);
        response.setCompletedLessons(completedLessons);
        response.setStartedLessons(startedLessons);
        response.setProgressPercentage(progressPercentage);
        response.setLessonProgresses(lessonProgresses);

        // Set last updated timestamp
        response.setLastUpdated(userProgress.stream()
                .map(LessonProgress::getUpdatedAt)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now()));

        return response;
    }

    /**
     * Calculate progress for a single module
     */
    private ModuleProgressInfo calculateModuleProgress(String userId, Module module, Map<Long, LessonProgress> progressMap) {
        ModuleProgressInfo moduleProgress = new ModuleProgressInfo(module.getId(), module.getTitle());

        int totalLessons = module.getLessons().size();
        if (totalLessons == 0) {
            moduleProgress.setProgressPercentage(0.0);
            return moduleProgress;
        }

        int completedLessons = 0;
        int startedLessons = 0;

        for (Lesson lesson : module.getLessons()) {
            LessonProgress progress = progressMap.get(lesson.getId());
            if (progress != null) {
                startedLessons++;
                if (progress.getCompleted()) {
                    completedLessons++;
                }
            }
        }

        double progressPercentage = (double) completedLessons / totalLessons * 100;

        moduleProgress.setTotalLessons(totalLessons);
        moduleProgress.setCompletedLessons(completedLessons);
        moduleProgress.setStartedLessons(startedLessons);
        moduleProgress.setProgressPercentage(progressPercentage);

        return moduleProgress;
    }

    /**
     * Create lesson progress info from lesson and progress data
     */
    private LessonProgressInfo createLessonProgressInfo(Lesson lesson, LessonProgress progress) {
        LessonProgressInfo info = new LessonProgressInfo(lesson.getId(), lesson.getTitle(), lesson.getType());

        if (progress != null) {
            info.setCompleted(progress.getCompleted());
            info.setCompletionPercentage(progress.getCompletionPercentage());
            info.setStartedAt(progress.getStartedAt());
            info.setCompletedAt(progress.getCompletedAt());
        } else {
            info.setCompleted(false);
            info.setCompletionPercentage(0);
        }

        return info;
    }

    /**
     * Check if user has completed a course
     */
    @Transactional(readOnly = true)
    public boolean isCourseCompleted(String userId, Long courseId) {
        Long totalLessons = courseRepository.countLessonsByCourseId(courseId);
        Long completedLessons = lessonProgressRepository.countCompletedLessonsByUserIdAndCourseId(userId, courseId);

        return totalLessons > 0 && totalLessons.equals(completedLessons);
    }

    /**
     * Check if user has completed a module
     */
    @Transactional(readOnly = true)
    public boolean isModuleCompleted(String userId, Long moduleId) {
        Long totalLessons = moduleRepository.countLessonsByModuleId(moduleId);
        Long completedLessons = lessonProgressRepository.countCompletedLessonsByUserIdAndModuleId(userId, moduleId);

        return totalLessons > 0 && totalLessons.equals(completedLessons);
    }
}