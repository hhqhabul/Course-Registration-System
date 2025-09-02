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

@Service
@RequiredArgsConstructor
public class EnrollmentService {
  private final EnrollmentRepo enrollmentRepo;
  private final StudentRepo studentRepo;
  private final CourseRepo courseRepo;

  @Transactional
  public Enrollment enroll(String studentId, String courseId) {
    Student s = studentRepo.findById(studentId)
      .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    Course c = courseRepo.findById(courseId)
      .orElseThrow(() -> new IllegalArgumentException("Course not found"));

    // duplicate check
    if (enrollmentRepo.existsByStudentIdAndCourseId(studentId, courseId))
      throw new IllegalStateException("Already enrolled");

    // capacity check
    int current = enrollmentRepo.countByCourseId(courseId);
    if (current >= c.getCapacity())
      throw new IllegalStateException("Course is full");

    // time conflict check (same day & overlapping time)
    boolean conflict = s.getEnrollments().stream()
      .map(Enrollment::getCourse)
      .filter(ec -> ec.getDayOfWeek() == c.getDayOfWeek())
      .anyMatch(ec -> c.getStartTime().isBefore(ec.getEndTime())
                   && ec.getStartTime().isBefore(c.getEndTime()));
    if (conflict) throw new IllegalStateException("Time conflict with another course");

    return enrollmentRepo.save(Enrollment.builder().student(s).course(c).build());
  }

  @Transactional
  public void drop(String enrollmentId) {
    enrollmentRepo.deleteById(enrollmentId);
  }
}
