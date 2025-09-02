package com.acme.regsys.service;

import com.acme.regsys.domain.Student;
import com.acme.regsys.repo.StudentRepo;
import com.acme.regsys.dto.*;
import com.acme.regsys.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthService {
  private final StudentRepo students;
  private final PasswordEncoder encoder;

  public void signup(SignupReq req) {
    if (students.findByEmail(req.email()).isPresent())
      throw new IllegalStateException("Email already in use");
    var s = Student.builder()
      .name(req.name())
      .email(req.email())
      .passwordHash(encoder.encode(req.password()))
      .build();
    students.save(s);
  }

  public TokenRes login(LoginReq req) {
    var s = students.findByEmail(req.email())
      .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!encoder.matches(req.password(), s.getPasswordHash()))
      throw new IllegalArgumentException("Invalid credentials");
    return new TokenRes(JwtUtil.generate(s.getEmail()));
  }
}
