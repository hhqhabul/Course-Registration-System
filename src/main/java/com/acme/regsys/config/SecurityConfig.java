package com.acme.regsys.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // APIs usually use tokens, so CSRF off for now
      .authorizeHttpRequests(auth -> auth
        .requestMatchers(
          "/api/health",            // public health check
          "/api/auth/**",           // (optional) your signup/login later
          "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html" // (optional) docs
        ).permitAll()
        .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults()) // simple 401 instead of redirect
      .formLogin(form -> form.disable());   // disable HTML login page

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
