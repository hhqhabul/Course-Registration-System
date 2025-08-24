package com.acme.regsys.dto;

public record EnrollmentDto(
    String id,
    String courseId,
    String courseCode,
    String courseTitle
) {}
