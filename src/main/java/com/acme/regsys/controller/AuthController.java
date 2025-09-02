package com.acme.regsys.controller;

import com.acme.regsys.dto.*;
import com.acme.regsys.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService auth;

  @PostMapping("/signup")
  public void signup(@Valid @RequestBody SignupReq req) { auth.signup(req); }

  @PostMapping("/login")
  public TokenRes login(@Valid @RequestBody LoginReq req) { return auth.login(req); }

  @GetMapping("/me")
  public String me(org.springframework.security.core.Authentication a) {
    return a == null ? null : a.getName(); // returns email
  }
}
