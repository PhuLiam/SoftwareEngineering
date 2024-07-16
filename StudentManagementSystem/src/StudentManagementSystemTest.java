import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Student Management System Tests")
public class StudentManagementSystemTest {

    @Nested
    @DisplayName("Tests for getCourseName method")
    class GetCourseNameTests {

        @Test
        @DisplayName("Should return correct course name for valid course code")
        void shouldReturnCorrectCourseNameForValidCourseCode() {
            StudentManagementSystem sms = new StudentManagementSystem();

            assertEquals("Programming Foundations", sms.getCourseName(101));
            assertEquals("User Interface Design", sms.getCourseName(102));
        }

        @Test
        @DisplayName("Should return 'Unknown Course' for invalid course code")
        void shouldReturnUnknownCourseForInvalidCourseCode() {
            StudentManagementSystem sms = new StudentManagementSystem();

            assertEquals("Unknown Course", sms.getCourseName(107)); // Non-existent course
        }
    }

    @Nested
    @DisplayName("Tests for Student class methods")
    class StudentClassTests {

        @Test
        @DisplayName("Should calculate total fees correctly for enrolled courses")
        void shouldCalculateTotalFeesCorrectly() {
            ArrayList<Integer> courses = new ArrayList<>(Arrays.asList(101, 102, 103));
            Student student = new Student("John", 2, courses, 20101);

            assertEquals(5000, student.calculateTotalFees()); // 1200 + 1800 + 2000 = 5000
        }

        @Test
        @DisplayName("Should return correct course fees for valid course code")
        void shouldReturnCorrectCourseFeesForValidCourseCode() {
            ArrayList<Integer> courses = new ArrayList<>(Arrays.asList(101));
            Student student = new Student("John", 2, courses, 20101);

            assertEquals(1200, student.getCourseFees(101));
        }

        @Test
        @DisplayName("Should return 0 for invalid course code")
        void shouldReturnZeroForInvalidCourseCode() {
            ArrayList<Integer> courses = new ArrayList<>(Arrays.asList(101));
            Student student = new Student("John", 2, courses, 20101);

            assertEquals(0, student.getCourseFees(107)); // Non-existent course
        }
    }
}
