package com.example.minilms.controller;

import com.example.minilms.dto.request.CourseCreateRequest;
import com.example.minilms.dto.response.ApiResponse;
import com.example.minilms.dto.response.ProgressResponse;
import com.example.minilms.entity.Course;
import com.example.minilms.service.CourseService;
import com.example.minilms.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Course management
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Course management APIs")
public class CourseController {

    private final CourseService courseService;
    private final ProgressService progressService;

    @Autowired
    public CourseController(CourseService courseService, ProgressService progressService) {
        this.courseService = courseService;
        this.progressService = progressService;
    }

    /**
     * Create a new course
     */
    @PostMapping
    @Operation(summary = "Create a new course", description = "Creates a new course with the provided details")
    public ResponseEntity<ApiResponse<Course>> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        try {
            Course course = courseService.createCourse(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Course created successfully", course));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create course: " + e.getMessage()));
        }
    }

    /**
     * Get all courses
     */
    @GetMapping
    @Operation(summary = "Get all courses", description = "Retrieves all courses with optional pagination")
    public ResponseEntity<ApiResponse<Object>> getAllCourses(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Include modules") @RequestParam(defaultValue = "false") boolean includeModules) {

        try {
            if (size > 0) {
                // Return paginated results
                Pageable pageable = PageRequest.of(page, size);
                Page<Course> coursePage = courseService.getAllCourses(pageable);
                return ResponseEntity.ok(ApiResponse.success("Courses retrieved successfully", coursePage));
            } else {
                // Return all courses
                List<Course> courses = includeModules ?
                        courseService.getAllCoursesWithModules() :
                        courseService.getAllCourses();
                return ResponseEntity.ok(ApiResponse.success("Courses retrieved successfully", courses));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve courses: " + e.getMessage()));
        }
    }

    /**
     * Get course by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID", description = "Retrieves a course by its ID with modules and lessons")
    public ResponseEntity<ApiResponse<Course>> getCourseById(
            @Parameter(description = "Course ID") @PathVariable Long id) {

        try {
            Course course = courseService.getCourseWithDetails(id)
                    .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

            return ResponseEntity.ok(ApiResponse.success("Course retrieved successfully", course));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve course: " + e.getMessage()));
        }
    }

    /**
     * Update course
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update course", description = "Updates an existing course")
    public ResponseEntity<ApiResponse<Course>> updateCourse(
            @Parameter(description = "Course ID") @PathVariable Long id,
            @Valid @RequestBody CourseCreateRequest request) {

        try {
            Course course = courseService.updateCourse(id, request);
            return ResponseEntity.ok(ApiResponse.success("Course updated successfully", course));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update course: " + e.getMessage()));
        }
    }

    /**
     * Delete course
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete course", description = "Deletes a course and all its modules and lessons")
    public ResponseEntity<ApiResponse<String>> deleteCourse(
            @Parameter(description = "Course ID") @PathVariable Long id) {

        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok(ApiResponse.success("Course deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete course: " + e.getMessage()));
        }
    }

    /**
     * Search courses by title
     */
    @GetMapping("/search")
    @Operation(summary = "Search courses", description = "Searches courses by title")
    public ResponseEntity<ApiResponse<List<Course>>> searchCourses(
            @Parameter(description = "Search term") @RequestParam String title) {

        try {
            List<Course> courses = courseService.searchCoursesByTitle(title);
            return ResponseEntity.ok(ApiResponse.success("Search completed successfully", courses));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to search courses: " + e.getMessage()));
        }
    }

    /**
     * Get course progress for a user
     */
    @GetMapping("/{id}/progress")
    @Operation(summary = "Get course progress", description = "Retrieves course progress for a specific user")
    public ResponseEntity<ApiResponse<ProgressResponse>> getCourseProgress(
            @Parameter(description = "Course ID") @PathVariable Long id,
            @Parameter(description = "User ID") @RequestParam String userId) {

        try {
            ProgressResponse progress = progressService.getCourseProgress(userId, id);
            return ResponseEntity.ok(ApiResponse.success("Course progress retrieved successfully", progress));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve course progress: " + e.getMessage()));
        }
    }
}