package com.example.minilms.repository;

import com.example.minilms.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Module entity operations
 */
@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    /**
     * Find all modules belonging to a specific course
     */
    List<Module> findByCourseIdOrderByIdAsc(Long courseId);

    /**
     * Find module by ID with lessons eagerly loaded
     */
    @Query("SELECT m FROM Module m " +
            "LEFT JOIN FETCH m.lessons l " +
            "WHERE m.id = :id " +
            "ORDER BY l.orderIndex ASC, l.id ASC")
    Optional<Module> findByIdWithLessons(@Param("id") Long id);

    /**
     * Find all modules with their lessons for a specific course
     */
    @Query("SELECT DISTINCT m FROM Module m " +
            "LEFT JOIN FETCH m.lessons l " +
            "WHERE m.course.id = :courseId " +
            "ORDER BY m.id ASC, l.orderIndex ASC, l.id ASC")
    List<Module> findByCourseIdWithLessons(@Param("courseId") Long courseId);

    /**
     * Count total number of lessons in a module
     */
    @Query("SELECT COUNT(l) FROM Module m " +
            "JOIN m.lessons l " +
            "WHERE m.id = :moduleId")
    Long countLessonsByModuleId(@Param("moduleId") Long moduleId);

    /**
     * Check if module belongs to a specific course
     */
    boolean existsByIdAndCourseId(Long id, Long courseId);
}