package com.example.minilms.service;

import com.example.minilms.dto.request.CourseCreateRequest;
import com.example.minilms.entity.Course;
import com.example.minilms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling course-related operations
 */
@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Create a new course
     */
    public Course createCourse(CourseCreateRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setCoverImageUrl(request.getCoverImageUrl());

        return courseRepository.save(course);
    }

    /**
     * Get all courses
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Get all courses with modules
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCoursesWithModules() {
        return courseRepository.findAllWithModules();
    }

    /**
     * Get courses with pagination
     */
    @Transactional(readOnly = true)
    public Page<Course> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    /**
     * Get course by ID
     */
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Get course by ID with modules and lessons
     */
    @Transactional(readOnly = true)
    public Optional<Course> getCourseWithDetails(Long id) {
        return courseRepository.findByIdWithModulesAndLessons(id);
    }

    /**
     * Update an existing course
     */
    public Course updateCourse(Long id, CourseCreateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setThumbnailUrl(request.getThumbnailUrl());
        course.setCoverImageUrl(request.getCoverImageUrl());

        return courseRepository.save(course);
    }

    /**
     * Delete a course
     */
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    /**
     * Search courses by title
     */
    @Transactional(readOnly = true)
    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Check if course exists
     */
    @Transactional(readOnly = true)
    public boolean courseExists(Long id) {
        return courseRepository.existsById(id);
    }

    /**
     * Get total lesson count for a course
     */
    @Transactional(readOnly = true)
    public Long getTotalLessonsInCourse(Long courseId) {
        return courseRepository.countLessonsByCourseId(courseId);
    }
}