package com.acme.regsys.controller;

import com.acme.regsys.domain.Student;
import com.acme.regsys.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {
  private final StudentRepo students;

  @GetMapping("/students")
  public List<Student> list() { return students.findAll(); }
}
