package com.example.minilms.controller;

import com.example.minilms.dto.request.LessonCreateRequest;
import com.example.minilms.dto.response.ApiResponse;
import com.example.minilms.entity.Lesson;
import com.example.minilms.entity.LessonProgress;
import com.example.minilms.entity.LessonType;
import com.example.minilms.service.LessonService;
import com.example.minilms.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Lesson management
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Lessons", description = "Lesson management APIs")
public class LessonController {

    private final LessonService lessonService;
    private final ProgressService progressService;

    @Autowired
    public LessonController(LessonService lessonService, ProgressService progressService) {
        this.lessonService = lessonService;
        this.progressService = progressService;
    }

    /**
     * Create a new lesson within a module
     */
    @PostMapping("/modules/{moduleId}/lessons")
    @Operation(summary = "Add lesson to module", description = "Creates a new lesson within a specific module")
    public ResponseEntity<ApiResponse<Lesson>> createLesson(
            @Parameter(description = "Module ID") @PathVariable Long moduleId,
            @Valid @RequestBody LessonCreateRequest request) {

        try {
            Lesson lesson = lessonService.createLesson(moduleId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Lesson created successfully", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create lesson: " + e.getMessage()));
        }
    }

    /**
     * Get all lessons for a module
     */
    @GetMapping("/modules/{moduleId}/lessons")
    @Operation(summary = "Get lessons by module", description = "Retrieves all lessons for a specific module")
    public ResponseEntity<ApiResponse<List<Lesson>>> getLessonsByModule(
            @Parameter(description = "Module ID") @PathVariable Long moduleId,
            @Parameter(description = "Filter by lesson type") @RequestParam(required = false) LessonType type) {

        try {
            List<Lesson> lessons = type != null ?
                    lessonService.getLessonsByModuleIdAndType(moduleId, type) :
                    lessonService.getLessonsByModuleId(moduleId);

            return ResponseEntity.ok(ApiResponse.success("Lessons retrieved successfully", lessons));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve lessons: " + e.getMessage()));
        }
    }

    /**
     * Get lesson by ID
     */
    @GetMapping("/lessons/{id}")
    @Operation(summary = "Get single lesson", description = "Retrieves a lesson by its ID")
    public ResponseEntity<ApiResponse<Lesson>> getLessonById(
            @Parameter(description = "Lesson ID") @PathVariable Long id) {

        try {
            Lesson lesson = lessonService.getLessonById(id)
                    .orElseThrow(() -> new RuntimeException("Lesson not found with id: " + id));

            return ResponseEntity.ok(ApiResponse.success("Lesson retrieved successfully", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve lesson: " + e.getMessage()));
        }
    }

    /**
     * Update lesson
     */
    @PutMapping("/lessons/{id}")
    @Operation(summary = "Update lesson", description = "Updates an existing lesson")
    public ResponseEntity<ApiResponse<Lesson>> updateLesson(
            @Parameter(description = "Lesson ID") @PathVariable Long id,
            @Valid @RequestBody LessonCreateRequest request) {

        try {
            Lesson lesson = lessonService.updateLesson(id, request);
            return ResponseEntity.ok(ApiResponse.success("Lesson updated successfully", lesson));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update lesson: " + e.getMessage()));
        }
    }

    /**
     * Delete lesson
     */
    @DeleteMapping("/lessons/{id}")
    @Operation(summary = "Delete lesson", description = "Deletes a lesson")
    public ResponseEntity<ApiResponse<String>> deleteLesson(
            @Parameter(description = "Lesson ID") @PathVariable Long id) {

        try {
            lessonService.deleteLesson(id);
            return ResponseEntity.ok(ApiResponse.success("Lesson deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete lesson: " + e.getMessage()));
        }
    }

    /**
     * Mark lesson as completed
     */
    @PostMapping("/lessons/{lessonId}/progress")
    @Operation(summary = "Mark lesson as completed", description = "Marks a lesson as completed for a user")
    public ResponseEntity<ApiResponse<LessonProgress>> markLessonCompleted(
            @Parameter(description = "Lesson ID") @PathVariable Long lessonId,
            @Parameter(description = "User ID") @RequestParam String userId,
            @Parameter(description = "Completion percentage (0-100)")
            @RequestParam(required = false)
            @Min(value = 0, message = "Completion percentage must be between 0 and 100")
            @Max(value = 100, message = "Completion percentage must be between 0 and 100")
            Integer completionPercentage) {

        try {
            Boolean completed = completionPercentage == null || completionPercentage >= 100;
            LessonProgress progress = progressService.updateLessonProgress(
                    userId, lessonId, completed, completionPercentage);

            return ResponseEntity.ok(ApiResponse.success("Lesson progress updated successfully", progress));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update lesson progress: " + e.getMessage()));
        }
    }

    /**
     * Update lesson progress with percentage
     */
    @PutMapping("/lessons/{lessonId}/progress")
    @Operation(summary = "Update lesson progress", description = "Updates lesson progress percentage for a user")
    public ResponseEntity<ApiResponse<LessonProgress>> updateLessonProgress(
            @Parameter(description = "Lesson ID") @PathVariable Long lessonId,
            @Parameter(description = "User ID") @RequestParam String userId,
            @Parameter(description = "Completion percentage (0-100)")
            @RequestParam
            @Min(value = 0, message = "Completion percentage must be between 0 and 100")
            @Max(value = 100, message = "Completion percentage must be between 0 and 100")
            Integer completionPercentage) {

        try {
            LessonProgress progress = progressService.updateLessonProgress(
                    userId, lessonId, null, completionPercentage);

            return ResponseEntity.ok(ApiResponse.success("Lesson progress updated successfully", progress));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update lesson progress: " + e.getMessage()));
        }
    }

    /**
     * Reorder lessons within a module
     */
    @PutMapping("/modules/{moduleId}/lessons/reorder")
    @Operation(summary = "Reorder lessons", description = "Reorders lessons within a module")
    public ResponseEntity<ApiResponse<String>> reorderLessons(
            @Parameter(description = "Module ID") @PathVariable Long moduleId,
            @Parameter(description = "Ordered list of lesson IDs") @RequestBody List<Long> lessonIds) {

        try {
            lessonService.reorderLessons(moduleId, lessonIds);
            return ResponseEntity.ok(ApiResponse.success("Lessons reordered successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to reorder lessons: " + e.getMessage()));
        }
    }
}