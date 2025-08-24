package com.acme.regsys.repo;

import com.acme.regsys.domain.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepo extends JpaRepository<Enrollment, String> {
    boolean existsByStudentIdAndCourseId(String studentId, String courseId);
    int countByCourseId(String courseId);
}
