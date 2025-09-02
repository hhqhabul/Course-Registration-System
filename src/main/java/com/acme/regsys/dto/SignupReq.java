package com.acme.regsys.dto;

import jakarta.validation.constraints.*;

public record SignupReq(
  @NotBlank(message = "name is required") String name,
  @Email(message = "email must be valid") @NotBlank(message = "email is required") String email,
  @Size(min = 8, message = "password must be at least 8 chars") String password
) {}
