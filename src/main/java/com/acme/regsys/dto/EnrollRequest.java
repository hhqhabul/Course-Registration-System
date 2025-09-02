package com.acme.regsys.dto;

import jakarta.validation.constraints.*;

public record EnrollRequest(
  @NotBlank(message = "courseId is required") String courseId
) {}
