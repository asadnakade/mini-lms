# 📚 Mini LMS - Backend (Spring Boot)

## 🎯 Overview
This is a backend service for a **Mini Learning Management System (LMS)** built with **Spring Boot 3+**.  
It allows managing **Courses**, **Modules**, **Lessons**, and tracking **User Progress**.

The system supports:
- Creating and retrieving courses, modules, and lessons
- Tracking lesson, module, and course progress for users
- Calculating completion percentages
- Role-based access (optional feature)
- Swagger API documentation

---

## 🏗 Tech Stack
- **Java** 17+
- **Spring Boot** 3+
- **Spring Data JPA**
- **PostgreSQL** or **MySQL**
- **Swagger / OpenAPI** for API documentation

---

## 📦 Features

### 1. **Entities & Relationships**
- **Course**  
  - `id`, `title`, `description`, `thumbnailUrl`, `coverImageUrl`  
  - One-to-Many → Modules
- **Module**  
  - `id`, `title`, `summary`, `thumbnailUrl`, `coverImageUrl`  
  - Many-to-One → Course  
  - One-to-Many → Lessons
- **Lesson**  
  - `id`, `title`, `type` (`TEXT`, `VIDEO`, `IMAGE`, `PDF`), `content`  
  - Many-to-One → Module
- **LessonProgress**  
  - `id`, `userId`, `lessonId`, `completed` (boolean)  
  - Used for tracking completion per user

---

### 2. **REST API Endpoints**

#### Courses
- `POST /courses` → Create a new course
- `GET /courses` → List all courses
- `GET /courses/{id}` → Get course details with modules and progress

#### Modules
- `POST /courses/{courseId}/modules` → Add a module to a course
- `GET /modules/{id}` → Get module details with lessons and progress

#### Lessons
- `POST /modules/{moduleId}/lessons` → Add a lesson to a module
- `GET /lessons/{id}` → Get a single lesson

#### Progress
- `POST /lessons/{lessonId}/progress?userId={userId}` → Mark lesson as completed
- `GET /courses/{courseId}/progress?userId={userId}` → Get course-level progress
- `GET /modules/{moduleId}/progress?userId={userId}` → Get module-level progress

---

### 3. **Business Logic**
- **Module Progress** = `completedLessons / totalLessons`
- **Course Progress** = Average of module progresses
- Validate lesson content type based on `type`

---

## 🚀 Running the Application

### Prerequisites
- Java 17+
- Maven or Gradle
- PostgreSQL/MySQL database

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/asadnakade/mini-lms.git
   cd mini-lms
