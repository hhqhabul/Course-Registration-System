package com.acme.regsys.service;

import com.acme.regsys.domain.Course;
import com.acme.regsys.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    public List<Course> searchCourses(String query) {
        if (query == null || query.isBlank()) {
            return courseRepo.findAll();
        }
        return courseRepo.findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(query, query);
    }
}
