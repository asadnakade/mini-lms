package com.example.minilms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Mini LMS Backend
 *
 * This application provides:
 * - Course, Module, and Lesson management
 * - User progress tracking
 * - REST API endpoints
 * - Swagger documentation
 * - Basic authentication
 *
 * Access points:
 * - API: http://localhost:8080/api/
 * - Swagger UI: http://localhost:8080/swagger-ui.html
 * - H2 Console: http://localhost:8080/h2-console
 * - Health Check: http://localhost:8080/actuator/health
 */
@SpringBootApplication
public class MiniLmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniLmsApplication.class, args);

		System.out.println("\n" +
				"==============================================\n" +
				"🎓 Mini LMS Backend Started Successfully! 🎓\n" +
				"==============================================\n" +
				"📚 API Base URL: http://localhost:8080/api/\n" +
				"📖 Swagger UI: http://localhost:8080/swagger-ui.html\n" +
				"🗄️  H2 Console: http://localhost:8080/h2-console\n" +
				"❤️  Health Check: http://localhost:8080/actuator/health\n" +
				"\n" +
				"👤 Authentication:\n" +
				"   - Admin: admin/admin123\n" +
				"   - Student: student/student123\n" +
				"   - Instructor: instructor/instructor123\n" +
				"\n" +
				"🗄️  H2 Database Connection:\n" +
				"   - JDBC URL: jdbc:h2:mem:minilms\n" +
				"   - Username: sa\n" +
				"   - Password: password\n" +
				"==============================================\n"
		);
	}
}