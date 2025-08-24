package com.acme.regsys.repo;

import com.acme.regsys.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course, String> {
    List<Course> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(String code, String title);
}
