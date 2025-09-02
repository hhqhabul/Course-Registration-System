package com.acme.regsys.config;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 400 — Body validation (@Valid @RequestBody)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleBodyValidation(MethodArgumentNotValidException ex) {
    var fields = new LinkedHashMap<String, String>();
    ex.getBindingResult().getFieldErrors()
      .forEach(e -> fields.put(e.getField(), e.getDefaultMessage()));
    return Map.of("error", "validation_failed", "fields", fields);
  }

  // 400 — Query/path validation (@Validated on params), or binder issues
  @ExceptionHandler({ ConstraintViolationException.class, BindException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, Object> handleParamValidation(Exception ex) {
    return Map.of("error", "validation_failed", "message", ex.getMessage());
  }

  // 401 — bad login credentials (use in AuthService)
  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public Map<String, String> handleUnauthorized(IllegalArgumentException ex) {
    return Map.of("error", ex.getMessage());
  }

  // 409 — business rule conflicts (duplicate enroll, full course, time overlap)
  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public Map<String, String> handleConflict(IllegalStateException ex) {
    return Map.of("error", ex.getMessage());
  }
}
