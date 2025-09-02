package com.acme.regsys.dto;

import jakarta.validation.constraints.*;

public record LoginReq(
  @Email(message = "email must be valid") @NotBlank(message = "email is required") String email,
  @NotBlank(message = "password is required") String password
) {}
