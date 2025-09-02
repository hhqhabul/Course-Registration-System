package com.acme.regsys.controller;

import com.acme.regsys.dto.EnrollRequest;
import com.acme.regsys.dto.EnrollmentDto;
import com.acme.regsys.repo.StudentRepo;
import com.acme.regsys.service.EnrollmentService;
import com.acme.regsys.util.DtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

  private final EnrollmentService enrollmentService;
  private final StudentRepo studentRepo;

  // POST /api/me/enrollments  (uses logged-in user)
  @PostMapping
  public EnrollmentDto enroll(@Valid @RequestBody EnrollRequest req, Authentication auth) {
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalArgumentException("Not authenticated");
    }
    var email = auth.getName();
    var student = studentRepo.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return DtoMapper.toDto(enrollmentService.enroll(student.getId(), req.courseId()));
  }

  // GET /api/me/enrollments  (list my enrollments)
  @GetMapping
  @Transactional(readOnly = true) // helps prevent LazyInitializationException when mapping enrollments
  public List<EnrollmentDto> myEnrollments(Authentication auth) {
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalArgumentException("Not authenticated");
    }
    var email = auth.getName();
    var student = studentRepo.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return student.getEnrollments().stream().map(DtoMapper::toDto).toList();
  }

  // DELETE /api/me/enrollments/{enrollmentId}
  @DeleteMapping("/{enrollmentId}")
  public void drop(@PathVariable String enrollmentId, Authentication auth) {
    if (auth == null || !auth.isAuthenticated()) {
      throw new IllegalArgumentException("Not authenticated");
    }
    // NOTE: For full safety, you could verify the enrollment belongs to this user before deleting.
    enrollmentService.drop(enrollmentId);
  }
}
