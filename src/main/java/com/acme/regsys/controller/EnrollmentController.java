package com.acme.regsys.controller;

import com.acme.regsys.domain.Enrollment;
import com.acme.regsys.repo.EnrollmentRepo;
import com.acme.regsys.repo.StudentRepo;
import com.acme.regsys.dto.EnrollRequest;
import com.acme.regsys.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

  private final EnrollmentService enrollmentService;
  private final EnrollmentRepo enrollmentRepo;
  private final StudentRepo studentRepo;

  // TEMP: we pass studentId in the body (until auth is added)
  @PostMapping
  public Enrollment enroll(@RequestBody EnrollRequest req) {
    return enrollmentService.enroll(req.studentId(), req.courseId());
  }

  // TEMP: list enrollments for a given studentId (query param)
  @GetMapping
  public List<Enrollment> myEnrollments(@RequestParam String studentId) {
    return studentRepo.findById(studentId)
      .map(s -> s.getEnrollments())
      .orElse(List.of());
  }

  @DeleteMapping("/{enrollmentId}")
  public void drop(@PathVariable String enrollmentId) {
    enrollmentRepo.deleteById(enrollmentId);
  }
}
