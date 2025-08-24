package com.acme.regsys.dto;

public record CourseDto(
    String id,
    String code,
    String title,
    int capacity,
    int credits,
    int dayOfWeek,
    String startTime,
    String endTime
) {}
