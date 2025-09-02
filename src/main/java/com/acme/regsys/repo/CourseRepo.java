package com.acme.regsys.repo;

import com.acme.regsys.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepo extends JpaRepository<Course, String> {

    Page<Course> findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(
        String code,
        String title,
        Pageable pageable
    );
}
