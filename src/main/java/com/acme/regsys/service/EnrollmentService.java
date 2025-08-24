package com.acme.regsys.service;

import com.acme.regsys.domain.Course;
import com.acme.regsys.domain.Enrollment;
import com.acme.regsys.domain.Student;
import com.acme.regsys.repo.CourseRepo;
import com.acme.regsys.repo.EnrollmentRepo;
import com.acme.regsys.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepo enrollmentRepo;
    private final StudentRepo studentRepo;
    private final CourseRepo courseRepo;

    @Transactional
    public Enrollment enroll(String studentId, String courseId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // 1. Check duplicate
        if (enrollmentRepo.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new IllegalStateException("Already enrolled in this course");
        }

        // 2. Check capacity
        int current = enrollmentRepo.countByCourseId(courseId);
        if (current >= course.getCapacity()) {
            throw new IllegalStateException("Course is full");
        }

        // time conflict check
        var sameDayEnrollments = student.getEnrollments().stream()
            .filter(e -> e.getCourse().getDayOfWeek() == course.getDayOfWeek())
            .map(Enrollment::getCourse)
            .toList();

        boolean overlaps = sameDayEnrollments.stream().anyMatch(c ->
            course.getStartTime().isBefore(c.getEndTime()) &&
            c.getStartTime().isBefore(course.getEndTime())
        );

        if (overlaps) {
            throw new IllegalStateException("Time conflict with another course.");
        }

        // 3. (Later) Add time conflict check

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        return enrollmentRepo.save(enrollment);
    }
}

