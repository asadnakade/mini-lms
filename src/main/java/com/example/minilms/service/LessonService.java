package com.example.minilms.service;

import com.example.minilms.dto.request.LessonCreateRequest;
import com.example.minilms.entity.Lesson;
import com.example.minilms.entity.LessonType;
import com.example.minilms.entity.Module;
import com.example.minilms.repository.LessonRepository;
import com.example.minilms.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling lesson-related operations
 */
@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
        this.lessonRepository = lessonRepository;
        this.moduleRepository = moduleRepository;
    }

    /**
     * Create a new lesson within a module
     */
    public Lesson createLesson(Long moduleId, LessonCreateRequest request) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId));

        // Validate content based on lesson type
        if (!isContentValidForType(request.getContent(), request.getType())) {
            throw new RuntimeException("Invalid content for lesson type: " + request.getType());
        }

        Lesson lesson = new Lesson();
        lesson.setTitle(request.getTitle());
        lesson.setType(request.getType());
        lesson.setContent(request.getContent());
        lesson.setModule(module);

        // Set order index if not provided
        if (request.getOrderIndex() == null) {
            Integer maxOrderIndex = lessonRepository.findMaxOrderIndexByModuleId(moduleId);
            lesson.setOrderIndex(maxOrderIndex + 1);
        } else {
            lesson.setOrderIndex(request.getOrderIndex());
        }

        return lessonRepository.save(lesson);
    }

    /**
     * Get all lessons for a module
     */
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByModuleId(Long moduleId) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found with id: " + moduleId);
        }
        return lessonRepository.findByModuleIdOrderByOrderIndexAscIdAsc(moduleId);
    }

    /**
     * Get lessons by module and type
     */
    @Transactional(readOnly = true)
    public List<Lesson> getLessonsByModuleIdAndType(Long moduleId, LessonType type) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found with id: " + moduleId);
        }
        return lessonRepository.findByModuleIdAndType(moduleId, type);
    }

    /**
     * Get lesson by ID
     */
    @Transactional(readOnly = true)
    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    /**
     * Update an existing lesson
     */
    public Lesson updateLesson(Long id, LessonCreateRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

        // Validate content based on lesson type
        if (!isContentValidForType(request.getContent(), request.getType())) {
            throw new RuntimeException("Invalid content for lesson type: " + request.getType());
        }

        lesson.setTitle(request.getTitle());
        lesson.setType(request.getType());
        lesson.setContent(request.getContent());

        if (request.getOrderIndex() != null) {
            lesson.setOrderIndex(request.getOrderIndex());
        }

        return lessonRepository.save(lesson);
    }

    /**
     * Delete a lesson
     */
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new RuntimeException("Lesson not found with id: " + id);
        }
        lessonRepository.deleteById(id);
    }

    /**
     * Check if lesson exists
     */
    @Transactional(readOnly = true)
    public boolean lessonExists(Long id) {
        return lessonRepository.existsById(id);
    }

    /**
     * Check if lesson belongs to a specific module
     */
    @Transactional(readOnly = true)
    public boolean lessonBelongsToModule(Long lessonId, Long moduleId) {
        return lessonRepository.existsByIdAndModuleId(lessonId, moduleId);
    }

    /**
     * Get lesson IDs for a course
     */
    @Transactional(readOnly = true)
    public List<Long> getLessonIdsByCourseId(Long courseId) {
        return lessonRepository.findLessonIdsByCourseId(courseId);
    }

    /**
     * Get lesson IDs for a module
     */
    @Transactional(readOnly = true)
    public List<Long> getLessonIdsByModuleId(Long moduleId) {
        return lessonRepository.findLessonIdsByModuleId(moduleId);
    }

    /**
     * Validate content based on lesson type
     */
    private boolean isContentValidForType(String content, LessonType type) {
        if (content == null || content.trim().isEmpty()) {
            return false;
        }

        return switch (type) {
            case TEXT -> content.length() <= 10000; // Max 10k characters for text
            case VIDEO -> isValidUrl(content) && (content.contains("youtube.com") ||
                    content.contains("vimeo.com") ||
                    content.contains("youtu.be") ||
                    content.endsWith(".mp4") ||
                    content.endsWith(".webm") ||
                    content.endsWith(".ogg"));
            case IMAGE -> isValidUrl(content) && (content.endsWith(".jpg") ||
                    content.endsWith(".jpeg") ||
                    content.endsWith(".png") ||
                    content.endsWith(".gif") ||
                    content.endsWith(".svg") ||
                    content.endsWith(".webp"));
            case PDF -> isValidUrl(content) && content.endsWith(".pdf");
        };
    }

    /**
     * Check if URL is valid
     */
    private boolean isValidUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }

    /**
     * Reorder lessons within a module
     */
    public void reorderLessons(Long moduleId, List<Long> lessonIds) {
        if (!moduleRepository.existsById(moduleId)) {
            throw new RuntimeException("Module not found with id: " + moduleId);
        }

        for (int i = 0; i < lessonIds.size(); i++) {
            Long lessonId = lessonIds.get(i);
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + lessonId));

            if (!lesson.getModule().getId().equals(moduleId)) {
                throw new RuntimeException("Lesson " + lessonId + " does not belong to module " + moduleId);
            }

            lesson.setOrderIndex(i + 1);
            lessonRepository.save(lesson);
        }
    }
}