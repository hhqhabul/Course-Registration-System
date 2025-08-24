package com.acme.regsys.controller;

import com.acme.regsys.domain.Course;
import com.acme.regsys.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
  private final CourseService courseService;

  @GetMapping
  public List<Course> list(@RequestParam(required = false) String query) {
    return courseService.searchCourses(query);
  }
}
