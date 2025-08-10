package com.example.minilms.service;

import com.example.minilms.dto.request.ModuleCreateRequest;
import com.example.minilms.entity.Course;
import com.example.minilms.entity.Module;
import com.example.minilms.repository.CourseRepository;
import com.example.minilms.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling module-related operations
 */
@Service
@Transactional
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
    }

    /**
     * Create a new module within a course
     */
    public Module createModule(Long courseId, ModuleCreateRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        Module module = new Module();
        module.setTitle(request.getTitle());
        module.setSummary(request.getSummary());
        module.setThumbnailUrl(request.getThumbnailUrl());
        module.setCoverImageUrl(request.getCoverImageUrl());
        module.setCourse(course);

        return moduleRepository.save(module);
    }

    /**
     * Get all modules for a course
     */
    @Transactional(readOnly = true)
    public List<Module> getModulesByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
        return moduleRepository.findByCourseIdOrderByIdAsc(courseId);
    }

    /**
     * Get modules with lessons for a course
     */
    @Transactional(readOnly = true)
    public List<Module> getModulesWithLessonsByCourseId(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
        return moduleRepository.findByCourseIdWithLessons(courseId);
    }

    /**
     * Get module by ID
     */
    @Transactional(readOnly = true)
    public Optional<Module> getModuleById(Long id) {
        return moduleRepository.findById(id);
    }

    /**
     * Get module by ID with lessons
     */
    @Transactional(readOnly = true)
    public Optional<Module> getModuleWithLessons(Long id) {
        return moduleRepository.findByIdWithLessons(id);
    }

    /**
     * Update an existing module
     */
    public Module updateModule(Long id, ModuleCreateRequest request) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

        module.setTitle(request.getTitle());
        module.setSummary(request.getSummary());
        module.setThumbnailUrl(request.getThumbnailUrl());
        module.setCoverImageUrl(request.getCoverImageUrl());

        return moduleRepository.save(module);
    }

    /**
     * Delete a module
     */
    public void deleteModule(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new RuntimeException("Module not found with id: " + id);
        }
        moduleRepository.deleteById(id);
    }

    /**
     * Check if module exists
     */
    @Transactional(readOnly = true)
    public boolean moduleExists(Long id) {
        return moduleRepository.existsById(id);
    }

    /**
     * Check if module belongs to a specific course
     */
    @Transactional(readOnly = true)
    public boolean moduleBelongsToCourse(Long moduleId, Long courseId) {
        return moduleRepository.existsByIdAndCourseId(moduleId, courseId);
    }

    /**
     * Get total lesson count for a module
     */
    @Transactional(readOnly = true)
    public Long getTotalLessonsInModule(Long moduleId) {
        return moduleRepository.countLessonsByModuleId(moduleId);
    }
}