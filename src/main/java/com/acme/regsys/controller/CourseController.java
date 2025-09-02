package com.acme.regsys.controller;

import com.acme.regsys.domain.Course;
import com.acme.regsys.dto.CourseDto;
import com.acme.regsys.repo.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepo courseRepo;

    /**
     * GET /api/courses
     * Optional search: ?query=comp
     * Pagination/sort: ?page=0&size=20&sort=code,asc
     */
    @GetMapping
    public Page<CourseDto> list(
            @RequestParam(required = false) String query,
            @PageableDefault(size = 20, sort = "code") Pageable pageable) {

        String q = (query == null) ? "" : query.trim();

        Page<Course> page = q.isEmpty()
                ? courseRepo.findAll(pageable)
                : courseRepo.findByCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(q, q, pageable);

        return page.map(c -> new CourseDto(
                c.getId(),
                c.getCode(),
                c.getTitle(),
                c.getCapacity(),
                c.getCredits(),
                c.getDayOfWeek(),
                c.getStartTime().toString(),
                c.getEndTime().toString()
        ));
    }
}
