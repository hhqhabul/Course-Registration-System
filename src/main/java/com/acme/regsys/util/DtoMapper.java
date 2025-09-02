package com.acme.regsys.util;

import com.acme.regsys.domain.Course;
import com.acme.regsys.domain.Enrollment;
import com.acme.regsys.dto.CourseDto;
import com.acme.regsys.dto.EnrollmentDto;

public class DtoMapper {

    // Convert Course → CourseDto
    public static CourseDto toDto(Course c) {
        return new CourseDto(
            c.getId(),
            c.getCode(),
            c.getTitle(),
            c.getCapacity(),
            c.getCredits(),
            c.getDayOfWeek(),
            c.getStartTime().toString(),
            c.getEndTime().toString()
        );
    }

    // Convert Enrollment → EnrollmentDto
    public static EnrollmentDto toDto(Enrollment e) {
        var c = e.getCourse();
        return new EnrollmentDto(
            e.getId(),
            c.getId(),
            c.getCode(),
            c.getTitle()
        );
    }
}
