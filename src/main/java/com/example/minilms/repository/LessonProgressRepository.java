package com.example.minilms.repository;

import com.example.minilms.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for LessonProgress entity operations
 */
@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    /**
     * Find progress record for a specific user and lesson
     */
    Optional<LessonProgress> findByUserIdAndLessonId(String userId, Long lessonId);

    /**
     * Find all progress records for a user
     */
    List<LessonProgress> findByUserId(String userId);

    /**
     * Find progress records for a user within a specific module
     */
    @Query("SELECT lp FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.id = :moduleId)")
    List<LessonProgress> findByUserIdAndModuleId(@Param("userId") String userId, @Param("moduleId") Long moduleId);

    /**
     * Find progress records for a user within a specific course
     */
    @Query("SELECT lp FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.course.id = :courseId)")
    List<LessonProgress> findByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Long courseId);

    /**
     * Count completed lessons for a user in a specific module
     */
    @Query("SELECT COUNT(lp) FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.completed = true " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.id = :moduleId)")
    Long countCompletedLessonsByUserIdAndModuleId(@Param("userId") String userId, @Param("moduleId") Long moduleId);

    /**
     * Count completed lessons for a user in a specific course
     */
    @Query("SELECT COUNT(lp) FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.completed = true " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.course.id = :courseId)")
    Long countCompletedLessonsByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Long courseId);

    /**
     * Get progress statistics for a user in a module
     */
    @Query("SELECT new map(" +
            "COUNT(lp) as totalStarted, " +
            "SUM(CASE WHEN lp.completed = true THEN 1 ELSE 0 END) as totalCompleted, " +
            "AVG(lp.completionPercentage) as averageProgress) " +
            "FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.id = :moduleId)")
    Object[] getProgressStatsByUserIdAndModuleId(@Param("userId") String userId, @Param("moduleId") Long moduleId);

    /**
     * Get progress statistics for a user in a course
     */
    @Query("SELECT new map(" +
            "COUNT(lp) as totalStarted, " +
            "SUM(CASE WHEN lp.completed = true THEN 1 ELSE 0 END) as totalCompleted, " +
            "AVG(lp.completionPercentage) as averageProgress) " +
            "FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.course.id = :courseId)")
    Object[] getProgressStatsByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") Long courseId);

    /**
     * Check if user has started any lesson in a course
     */
    @Query("SELECT COUNT(lp) > 0 FROM LessonProgress lp " +
            "WHERE lp.userId = :userId " +
            "AND lp.lessonId IN (SELECT l.id FROM Lesson l WHERE l.module.course.id = :courseId)")
    boolean hasUserStartedCourse(@Param("userId") String userId, @Param("courseId") Long courseId);
}