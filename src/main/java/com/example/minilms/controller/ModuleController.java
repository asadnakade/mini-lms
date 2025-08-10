package com.example.minilms.controller;

import com.example.minilms.dto.request.ModuleCreateRequest;
import com.example.minilms.dto.response.ApiResponse;
import com.example.minilms.dto.response.ProgressResponse;
import com.example.minilms.entity.Module;
import com.example.minilms.service.ModuleService;
import com.example.minilms.service.ProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Module management
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Modules", description = "Module management APIs")
public class ModuleController {

    private final ModuleService moduleService;
    private final ProgressService progressService;

    @Autowired
    public ModuleController(ModuleService moduleService, ProgressService progressService) {
        this.moduleService = moduleService;
        this.progressService = progressService;
    }

    /**
     * Create a new module within a course
     */
    @PostMapping("/courses/{courseId}/modules")
    @Operation(summary = "Add module to course", description = "Creates a new module within a specific course")
    public ResponseEntity<ApiResponse<Module>> createModule(
            @Parameter(description = "Course ID") @PathVariable Long courseId,
            @Valid @RequestBody ModuleCreateRequest request) {

        try {
            Module module = moduleService.createModule(courseId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Module created successfully", module));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to create module: " + e.getMessage()));
        }
    }

    /**
     * Get all modules for a course
     */
    @GetMapping("/courses/{courseId}/modules")
    @Operation(summary = "Get modules by course", description = "Retrieves all modules for a specific course")
    public ResponseEntity<ApiResponse<List<Module>>> getModulesByCourse(
            @Parameter(description = "Course ID") @PathVariable Long courseId,
            @Parameter(description = "Include lessons") @RequestParam(defaultValue = "false") boolean includeLessons) {

        try {
            List<Module> modules = includeLessons ?
                    moduleService.getModulesWithLessonsByCourseId(courseId) :
                    moduleService.getModulesByCourseId(courseId);

            return ResponseEntity.ok(ApiResponse.success("Modules retrieved successfully", modules));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve modules: " + e.getMessage()));
        }
    }

    /**
     * Get module by ID
     */
    @GetMapping("/modules/{id}")
    @Operation(summary = "Get module with lessons and progress", description = "Retrieves a module by its ID with lessons")
    public ResponseEntity<ApiResponse<Module>> getModuleById(
            @Parameter(description = "Module ID") @PathVariable Long id) {

        try {
            Module module = moduleService.getModuleWithLessons(id)
                    .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

            return ResponseEntity.ok(ApiResponse.success("Module retrieved successfully", module));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve module: " + e.getMessage()));
        }
    }

    /**
     * Update module
     */
    @PutMapping("/modules/{id}")
    @Operation(summary = "Update module", description = "Updates an existing module")
    public ResponseEntity<ApiResponse<Module>> updateModule(
            @Parameter(description = "Module ID") @PathVariable Long id,
            @Valid @RequestBody ModuleCreateRequest request) {

        try {
            Module module = moduleService.updateModule(id, request);
            return ResponseEntity.ok(ApiResponse.success("Module updated successfully", module));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Failed to update module: " + e.getMessage()));
        }
    }

    /**
     * Delete module
     */
    @DeleteMapping("/modules/{id}")
    @Operation(summary = "Delete module", description = "Deletes a module and all its lessons")
    public ResponseEntity<ApiResponse<String>> deleteModule(
            @Parameter(description = "Module ID") @PathVariable Long id) {

        try {
            moduleService.deleteModule(id);
            return ResponseEntity.ok(ApiResponse.success("Module deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete module: " + e.getMessage()));
        }
    }

    /**
     * Get module progress for a user
     */
    @GetMapping("/modules/{id}/progress")
    @Operation(summary = "Get module-level progress", description = "Retrieves module progress for a specific user")
    public ResponseEntity<ApiResponse<ProgressResponse>> getModuleProgress(
            @Parameter(description = "Module ID") @PathVariable Long id,
            @Parameter(description = "User ID") @RequestParam String userId) {

        try {
            ProgressResponse progress = progressService.getModuleProgress(userId, id);
            return ResponseEntity.ok(ApiResponse.success("Module progress retrieved successfully", progress));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve module progress: " + e.getMessage()));
        }
    }
}