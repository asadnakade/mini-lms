package com.example.minilms.repository;

import com.example.minilms.entity.Lesson;
import com.example.minilms.entity.LessonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Lesson entity operations
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Find all lessons belonging to a specific module, ordered by orderIndex and then by ID
     */
    List<Lesson> findByModuleIdOrderByOrderIndexAscIdAsc(Long moduleId);

    /**
     * Find lessons by module ID and type
     */
    List<Lesson> findByModuleIdAndType(Long moduleId, LessonType type);

    /**
     * Find all lesson IDs for a specific course
     */
    @Query("SELECT l.id FROM Lesson l " +
            "WHERE l.module.course.id = :courseId")
    List<Long> findLessonIdsByCourseId(@Param("courseId") Long courseId);

    /**
     * Find all lesson IDs for a specific module
     */
    @Query("SELECT l.id FROM Lesson l " +
            "WHERE l.module.id = :moduleId")
    List<Long> findLessonIdsByModuleId(@Param("moduleId") Long moduleId);

    /**
     * Check if lesson belongs to a specific module
     */
    boolean existsByIdAndModuleId(Long id, Long moduleId);

    /**
     * Get the maximum order index for lessons in a module
     */
    @Query("SELECT COALESCE(MAX(l.orderIndex), 0) FROM Lesson l WHERE l.module.id = :moduleId")
    Integer findMaxOrderIndexByModuleId(@Param("moduleId") Long moduleId);
}