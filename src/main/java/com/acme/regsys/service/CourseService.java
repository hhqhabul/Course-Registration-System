package com.acme.regsys.service;

import com.acme.regsys.domain.Course;
import com.acme.regsys.dto.CourseDto;
import com.acme.regsys.repo.CourseRepo;
import com.acme.regsys.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepo courseRepo;

    @Transactional(readOnly = true)
    public Page<CourseDto> searchCourses(String query, Pageable pageable) {
        String q = query == null ? "" : query.trim();
        Page<Course> page = (q.isEmpty())
            ? courseRepo.findAll(pageable)
            : courseRepo.findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(q, q, pageable);
        return page.map(DtoMapper::toDto);
    }
}
