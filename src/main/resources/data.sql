-- Sample data for Mini LMS
-- This file will be executed automatically when the application starts

-- Insert sample courses
INSERT INTO courses (id, title, description, thumbnail_url, cover_image_url, created_at, updated_at) VALUES
(1, 'Introduction to Java Programming', 'Learn the fundamentals of Java programming language including OOP concepts, data structures, and basic algorithms.', 'https://example.com/java-thumb.jpg', 'https://example.com/java-cover.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Web Development with Spring Boot', 'Master modern web development using Spring Boot framework, including REST APIs, security, and database integration.', 'https://example.com/spring-thumb.jpg', 'https://example.com/spring-cover.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Database Design and SQL', 'Comprehensive course on database design principles, normalization, and advanced SQL queries.', 'https://example.com/db-thumb.jpg', 'https://example.com/db-cover.jpg', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample modules for Course 1 (Java Programming)
INSERT INTO modules (id, title, summary, thumbnail_url, cover_image_url, course_id, created_at, updated_at) VALUES
(1, 'Java Basics', 'Introduction to Java syntax, variables, and basic programming concepts.', 'https://example.com/java-basics-thumb.jpg', 'https://example.com/java-basics-cover.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Object-Oriented Programming', 'Learn about classes, objects, inheritance, polymorphism, and encapsulation.', 'https://example.com/oop-thumb.jpg', 'https://example.com/oop-cover.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Collections and Data Structures', 'Explore Java Collections Framework and fundamental data structures.', 'https://example.com/collections-thumb.jpg', 'https://example.com/collections-cover.jpg', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample modules for Course 2 (Spring Boot)
INSERT INTO modules (id, title, summary, thumbnail_url, cover_image_url, course_id, created_at, updated_at) VALUES
(4, 'Spring Boot Fundamentals', 'Getting started with Spring Boot, auto-configuration, and project structure.', 'https://example.com/spring-fundamentals-thumb.jpg', 'https://example.com/spring-fundamentals-cover.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(5, 'Building REST APIs', 'Create RESTful web services using Spring Boot and Spring MVC.', 'https://example.com/rest-apis-thumb.jpg', 'https://example.com/rest-apis-cover.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Data Access with JPA', 'Learn Spring Data JPA for database operations and entity management.', 'https://example.com/jpa-thumb.jpg', 'https://example.com/jpa-cover.jpg', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample modules for Course 3 (Database Design)
INSERT INTO modules (id, title, summary, thumbnail_url, cover_image_url, course_id, created_at, updated_at) VALUES
(7, 'Database Fundamentals', 'Introduction to databases, RDBMS concepts, and basic terminology.', 'https://example.com/db-fundamentals-thumb.jpg', 'https://example.com/db-fundamentals-cover.jpg', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'SQL Essentials', 'Master SQL queries including SELECT, INSERT, UPDATE, DELETE, and JOINs.', 'https://example.com/sql-essentials-thumb.jpg', 'https://example.com/sql-essentials-cover.jpg', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 1 (Java Basics)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(1, 'What is Java?', 'TEXT', 'Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'Setting up Java Development Environment', 'VIDEO', 'https://youtube.com/watch?v=java-setup-demo', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'Your First Java Program', 'TEXT', 'Let''s write our first Java program - the classic "Hello, World!" example. This lesson covers the basic structure of a Java program.', 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'Java Syntax Reference', 'PDF', 'https://example.com/java-syntax-reference.pdf', 4, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 2 (OOP)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(5, 'Understanding Classes and Objects', 'TEXT', 'A class is a blueprint for creating objects. An object is an instance of a class that contains both data (fields) and methods.', 1, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'Inheritance in Java', 'VIDEO', 'https://youtube.com/watch?v=java-inheritance-demo', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'Polymorphism Explained', 'TEXT', 'Polymorphism allows objects of different types to be treated as objects of a common base type. It enables a single interface to represent different underlying forms.', 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'OOP Best Practices', 'PDF', 'https://example.com/oop-best-practices.pdf', 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 3 (Collections)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(9, 'Introduction to Java Collections', 'TEXT', 'The Java Collections Framework provides a set of interfaces and classes to store and manipulate groups of objects efficiently.', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'Working with Lists and Sets', 'VIDEO', 'https://youtube.com/watch?v=java-collections-demo', 2, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'Maps and HashMap Usage', 'TEXT', 'Maps store key-value pairs and provide efficient lookup operations. HashMap is the most commonly used implementation.', 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 4 (Spring Boot Fundamentals)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(12, 'Introduction to Spring Boot', 'TEXT', 'Spring Boot is an extension of the Spring framework that simplifies the setup and development of Spring applications.', 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(13, 'Creating Your First Spring Boot Application', 'VIDEO', 'https://youtube.com/watch?v=spring-boot-first-app', 2, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(14, 'Understanding Auto-Configuration', 'TEXT', 'Spring Boot auto-configuration automatically configures your Spring application based on the dependencies present on the classpath.', 3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 5 (REST APIs)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(15, 'REST API Principles', 'TEXT', 'REST (Representational State Transfer) is an architectural style for designing web services that use HTTP methods effectively.', 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(16, 'Building REST Controllers', 'VIDEO', 'https://youtube.com/watch?v=spring-rest-controllers', 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(17, 'API Documentation with Swagger', 'TEXT', 'Swagger provides tools for documenting and testing REST APIs, making them easier to understand and use.', 3, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 6 (JPA)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(18, 'Introduction to JPA', 'TEXT', 'Java Persistence API (JPA) is a specification for managing relational data in Java applications.', 1, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(19, 'Entity Mapping and Relationships', 'VIDEO', 'https://youtube.com/watch?v=jpa-entity-mapping', 2, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(20, 'Spring Data JPA Repositories', 'TEXT', 'Spring Data JPA provides repository support for JPA, reducing boilerplate code for data access operations.', 3, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 7 (Database Fundamentals)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(21, 'What is a Database?', 'TEXT', 'A database is an organized collection of structured information, or data, typically stored electronically in a computer system.', 1, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(22, 'RDBMS Concepts', 'VIDEO', 'https://youtube.com/watch?v=rdbms-concepts', 2, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(23, 'Database Design Principles', 'PDF', 'https://example.com/database-design-principles.pdf', 3, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample lessons for Module 8 (SQL Essentials)
INSERT INTO lessons (id, title, type, content, order_index, module_id, created_at, updated_at) VALUES
(24, 'Basic SQL Queries', 'TEXT', 'SQL (Structured Query Language) is used to communicate with databases. Learn the basic SELECT statement and filtering data.', 1, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(25, 'Joins and Relationships', 'VIDEO', 'https://youtube.com/watch?v=sql-joins-tutorial', 2, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(26, 'Advanced SQL Functions', 'TEXT', 'Explore advanced SQL functions including aggregate functions, window functions, and stored procedures.', 3, 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert sample progress data for different users
INSERT INTO lesson_progress (id, user_id, lesson_id, completed, completion_percentage, started_at, completed_at, created_at, updated_at) VALUES
-- Student1 progress in Java course
(1, 'student1', 1, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, 'student1', 2, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(3, 'student1', 3, false, 50, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(4, 'student1', 5, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Student2 progress in Spring Boot course
(5, 'student2', 12, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(6, 'student2', 13, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(7, 'student2', 14, false, 25, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(8, 'student2', 15, false, 10, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

-- Student3 progress in Database course
(9, 'student3', 21, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(10, 'student3', 22, false, 75, CURRENT_TIMESTAMP, null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(11, 'student3', 24, true, 100, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);