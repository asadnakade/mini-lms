package com.example.minilms.repository;

import com.example.minilms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Course entity operations
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Find course by ID with modules and lessons eagerly loaded
     */
    @Query("SELECT DISTINCT c FROM Course c " +
            "LEFT JOIN FETCH c.modules m " +
            "LEFT JOIN FETCH m.lessons " +
            "WHERE c.id = :id")
    Optional<Course> findByIdWithModulesAndLessons(@Param("id") Long id);

    /**
     * Find all courses with their modules
     */
    @Query("SELECT DISTINCT c FROM Course c LEFT JOIN FETCH c.modules")
    List<Course> findAllWithModules();

    /**
     * Find courses by title containing the search term (case insensitive)
     */
    List<Course> findByTitleContainingIgnoreCase(String title);

    /**
     * Count total number of lessons in a course
     */
    @Query("SELECT COUNT(l) FROM Course c " +
            "JOIN c.modules m " +
            "JOIN m.lessons l " +
            "WHERE c.id = :courseId")
    Long countLessonsByCourseId(@Param("courseId") Long courseId);
}